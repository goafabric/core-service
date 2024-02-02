package org.goafabric.core.medicalrecords.logic.jpa;

import jakarta.transaction.Transactional;
import org.goafabric.core.medicalrecords.controller.dto.MedicalRecord;
import org.goafabric.core.medicalrecords.controller.dto.MedicalRecordAble;
import org.goafabric.core.medicalrecords.controller.dto.MedicalRecordType;
import org.goafabric.core.medicalrecords.logic.jpa.mapper.MedicalRecordMapper;
import org.goafabric.core.medicalrecords.repository.jpa.MedicalRecordRepository;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Transactional
@Profile("jpa")
public class MedicalRecordLogic implements org.goafabric.core.medicalrecords.logic.MedicalRecordLogic {

    private final MedicalRecordMapper mapper;

    private final MedicalRecordRepository repository;

    private final ApplicationContext applicationContext;

    public MedicalRecordLogic(MedicalRecordMapper mapper, MedicalRecordRepository repository, ApplicationContext applicationContext) {
        this.mapper = mapper;
        this.repository = repository;
        this.applicationContext = applicationContext;
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
