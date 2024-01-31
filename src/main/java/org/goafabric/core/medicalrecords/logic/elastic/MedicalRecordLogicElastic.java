package org.goafabric.core.medicalrecords.logic.elastic;

import org.goafabric.core.medicalrecords.controller.dto.MedicalRecord;
import org.goafabric.core.medicalrecords.controller.dto.MedicalRecordType;
import org.goafabric.core.medicalrecords.logic.MedicalRecordLogicAble;
import org.goafabric.core.medicalrecords.logic.elastic.mapper.MedicalRecordMapperElastic;
import org.goafabric.core.medicalrecords.repository.elastic.repository.MedicalRecordRepositoryElastic;
import org.goafabric.core.medicalrecords.repository.elastic.repository.entity.MedicalRecordElo;
import org.h2.util.StringUtils;
import org.springframework.context.annotation.Profile;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Component
@Transactional(propagation = Propagation.REQUIRES_NEW)
@Profile("elastic")
public class MedicalRecordLogicElastic implements MedicalRecordLogicAble {

    private final MedicalRecordMapperElastic mapper;

    private final MedicalRecordRepositoryElastic repository;

    private final ElasticsearchOperations elasticSearchOperations;

    public MedicalRecordLogicElastic(MedicalRecordMapperElastic mapper, MedicalRecordRepositoryElastic repository, ElasticsearchOperations elasticSearchOperations) {
        this.mapper = mapper;
        this.repository = repository;
        this.elasticSearchOperations = elasticSearchOperations;
    }

    public MedicalRecord getById(String id) {
        return mapper.map(repository.findById(id).get());
    }

    public MedicalRecord getByRelation(String relation) {
        return mapper.map(repository.findByRelation(relation));
    }


    public List<MedicalRecord> findByEncounterIdAndDisplay(String encounterId, String display) {
        var criteria = new Criteria("encounterId").is(encounterId);
        if (!StringUtils.isNullOrEmpty(display)) {
            Arrays.stream(display.split(" ")).forEach(token -> { // this is presumeably a hack
                criteria.subCriteria(
                        new Criteria("display").contains(token).or(new Criteria("display").fuzzy(token)
                        ));
            });
        }
        var hits = elasticSearchOperations.search(new CriteriaQuery(criteria), MedicalRecordElo.class);
        return mapper.map(hits.stream().map(SearchHit::getContent).toList());
    }

    public MedicalRecord save(MedicalRecord medicalRecord) {
        return mapper.map(
            repository.save(mapper.map(medicalRecord))
        );
    }

    public MedicalRecord saveRelatedRecord(String relation, String existingId, MedicalRecordType type, String display, String code) {
        if (existingId != null) {
            var medicalRecord = getByRelation(existingId);
            return save(new MedicalRecord(medicalRecord.id(), medicalRecord.encounterId(), medicalRecord.version(),
                    type, display, medicalRecord.code(), medicalRecord.relation()));
        } else {
            return save(new MedicalRecord(null, null, null, type, display, code, relation));
        }
    }


    public void delete(String id) {
        repository.deleteById(id);
    }
}
