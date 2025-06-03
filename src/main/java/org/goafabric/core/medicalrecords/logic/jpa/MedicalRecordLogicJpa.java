package org.goafabric.core.medicalrecords.logic.jpa;

import jakarta.transaction.Transactional;
import org.goafabric.core.medicalrecords.controller.dto.MedicalRecord;
import org.goafabric.core.medicalrecords.controller.dto.MedicalRecordAble;
import org.goafabric.core.medicalrecords.controller.dto.MedicalRecordType;
import org.goafabric.core.medicalrecords.logic.jpa.mapper.MedicalRecordMapper;
import org.goafabric.core.medicalrecords.persistence.jpa.MedicalRecordRepository;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Transactional
@Profile("jpa")
public class MedicalRecordLogicJpa implements org.goafabric.core.medicalrecords.logic.MedicalRecordLogic {

    private final MedicalRecordMapper mapper;

    private final MedicalRecordRepository repository;

    private final ApplicationContext applicationContext;

    public MedicalRecordLogicJpa(MedicalRecordMapper mapper, MedicalRecordRepository repository, ApplicationContext applicationContext) {
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

    //save Specialized records, has to be called by Specialized class like bodymetrics
    public MedicalRecord saveSpecializedRecord(String specialization, MedicalRecordAble medicalRecordAble) {
        return medicalRecordAble.id() != null
                ? updateSpecializedRecord(medicalRecordAble)
                : save(new MedicalRecord(medicalRecordAble.type(), medicalRecordAble.toDisplay(), medicalRecordAble.code(), specialization));
    }

    private MedicalRecord updateSpecializedRecord(MedicalRecordAble updatedRecord) {
        var medicalRecord = mapper.map(repository.findBySpecialization(updatedRecord.id()));
        return save(new MedicalRecord(medicalRecord.id(), medicalRecord.encounterId(), medicalRecord.version(), medicalRecord.type(),
                updatedRecord.toDisplay(), updatedRecord.code(), updatedRecord.id()));
    }

    public void delete(String id) {
        deleteSpecializedRecords(getById(id));
        repository.deleteById(id);
    }

    private void deleteSpecializedRecords(MedicalRecord medicalRecord) {
        Optional.ofNullable(medicalRecord.specialization())
                .ifPresent(specialization -> applicationContext.getBean(MedicalRecordType.getClassByType(medicalRecord.type()))
                        .delete(specialization));
    }

}
