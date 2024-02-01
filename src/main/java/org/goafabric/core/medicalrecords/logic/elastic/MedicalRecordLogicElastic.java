package org.goafabric.core.medicalrecords.logic.elastic;

import org.goafabric.core.medicalrecords.controller.dto.MedicalRecord;
import org.goafabric.core.medicalrecords.controller.dto.RecordAble;
import org.goafabric.core.medicalrecords.logic.MedicalRecordLogic;
import org.goafabric.core.medicalrecords.logic.RecordDeleteAble;
import org.goafabric.core.medicalrecords.logic.elastic.mapper.MedicalRecordMapperElastic;
import org.goafabric.core.medicalrecords.repository.elastic.repository.MedicalRecordRepositoryElastic;
import org.goafabric.core.medicalrecords.repository.elastic.repository.entity.MedicalRecordElo;
import org.h2.util.StringUtils;
import org.springframework.context.annotation.Lazy;
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
public class MedicalRecordLogicElastic implements MedicalRecordLogic {

    private final MedicalRecordMapperElastic mapper;

    private final MedicalRecordRepositoryElastic repository;

    private final ElasticsearchOperations elasticSearchOperations;

    private List<RecordDeleteAble> recordDeleteAbles;

    public MedicalRecordLogicElastic(MedicalRecordMapperElastic mapper, MedicalRecordRepositoryElastic repository, ElasticsearchOperations elasticSearchOperations, @Lazy List<RecordDeleteAble> recordDeleteAbles) {
        this.mapper = mapper;
        this.repository = repository;
        this.elasticSearchOperations = elasticSearchOperations;
        this.recordDeleteAbles = recordDeleteAbles;
    }

    public MedicalRecord getById(String id) {
        return mapper.map(repository.findById(id).get());
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

    public MedicalRecord saveRelatedRecord(String relation, RecordAble recordAble) {
        return recordAble.id() != null
                ? updateRelatedRecord(recordAble)
                : save(new MedicalRecord(null, null, null, recordAble.type(), recordAble.toDisplay(), recordAble.code(), relation));
    }

    private MedicalRecord updateRelatedRecord(RecordAble recordAble) {
        var medicalRecord = getByRelation(recordAble.id());
        return save(new MedicalRecord(medicalRecord.id(), medicalRecord.encounterId(), medicalRecord.version(),
                medicalRecord.type(), recordAble.toDisplay(), medicalRecord.code(), medicalRecord.relation()));
    }

    public void delete(String id) {
        var medicalRecord = getById(id);
        repository.deleteById(id);
        recordDeleteAbles.forEach(r -> r.delete(medicalRecord.relation()));
    }

    private MedicalRecord getByRelation(String relation) {
        return mapper.map(repository.findByRelation(relation));
    }

}
