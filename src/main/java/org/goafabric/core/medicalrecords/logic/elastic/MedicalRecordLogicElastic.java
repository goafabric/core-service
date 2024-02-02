package org.goafabric.core.medicalrecords.logic.elastic;

import org.goafabric.core.medicalrecords.controller.dto.MedicalRecord;
import org.goafabric.core.medicalrecords.controller.dto.MedicalRecordAble;
import org.goafabric.core.medicalrecords.controller.dto.MedicalRecordType;
import org.goafabric.core.medicalrecords.logic.MedicalRecordLogic;
import org.goafabric.core.medicalrecords.logic.elastic.mapper.MedicalRecordMapperElastic;
import org.goafabric.core.medicalrecords.repository.elastic.repository.MedicalRecordRepositoryElastic;
import org.goafabric.core.medicalrecords.repository.elastic.repository.entity.MedicalRecordElo;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Profile;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
@Transactional(propagation = Propagation.REQUIRES_NEW)
@Profile("elastic")
public class MedicalRecordLogicElastic implements MedicalRecordLogic {

    private final MedicalRecordMapperElastic mapper;

    private final MedicalRecordRepositoryElastic repository;

    private final ElasticsearchOperations elasticSearchOperations;

    private final ApplicationContext applicationContext;

    public MedicalRecordLogicElastic(MedicalRecordMapperElastic mapper, MedicalRecordRepositoryElastic repository, ElasticsearchOperations elasticSearchOperations, ApplicationContext applicationContext) {
        this.mapper = mapper;
        this.repository = repository;
        this.elasticSearchOperations = elasticSearchOperations;
        this.applicationContext = applicationContext;
    }
    
    public List<MedicalRecord> findByEncounterIdAndDisplay(String encounterId, String display) {  //called by EncounterLogic
        var criteria = new Criteria("encounterId").is(encounterId);
        if (StringUtils.hasText(display)) {
            Arrays.stream(display.split(" "))
                    .forEach(token -> criteria.subCriteria( //this is presumeably a hack
                            new Criteria("display").contains(token).or(new Criteria("display").fuzzy(token))
                    ));
        }
        return mapper.map(elasticSearchOperations.search(new CriteriaQuery(criteria), MedicalRecordElo.class));
    }

    public MedicalRecord getById(String id) {
        return mapper.map(repository.findById(id).get());
    }

    public MedicalRecord save(MedicalRecord medicalRecord) {
        return mapper.map(
            repository.save(mapper.map(medicalRecord))
        );
    }

    //save related records, has to be called by related class like bodymetrics
    public MedicalRecord saveRelatedRecord(String relation, MedicalRecordAble medicalRecordAble) {
        return medicalRecordAble.id() != null
                ? updateRelatedRecord(medicalRecordAble)
                : save(new MedicalRecord(medicalRecordAble.type(), medicalRecordAble.toDisplay(), medicalRecordAble.code(), relation));
    }

    private MedicalRecord updateRelatedRecord(MedicalRecordAble updatedRecord) {
        var medicalRecord = mapper.map(repository.findByRelation(updatedRecord.id()));
        return save(new MedicalRecord(medicalRecord.id(), medicalRecord.encounterId(), medicalRecord.version(), medicalRecord.type(),
                updatedRecord.toDisplay(), updatedRecord.code(), updatedRecord.id()));
    }

    public void delete(String id) {
        deleteRelatedRecords(getById(id));
        repository.deleteById(id);
    }

    private void deleteRelatedRecords(MedicalRecord medicalRecord) {
        Optional.ofNullable(medicalRecord.relation())
                .ifPresent(relation -> applicationContext.getBean(MedicalRecordType.getClassByType(medicalRecord.type()))
                        .delete(relation));
    }

}
