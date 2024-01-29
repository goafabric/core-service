package org.goafabric.core.medicalrecords.logic.jpa;

import jakarta.transaction.Transactional;
import org.goafabric.core.medicalrecords.controller.dto.MedicalRecord;
import org.goafabric.core.medicalrecords.logic.MedicalRecordLogicAble;
import org.goafabric.core.medicalrecords.logic.mapper.MedicalRecordMapper;
import org.goafabric.core.medicalrecords.repository.jpa.MedicalRecordRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Transactional
@Profile("jpa")
public class MedicalRecordLogic implements MedicalRecordLogicAble {

    private final MedicalRecordMapper mapper;

    private final MedicalRecordRepository repository;

    public MedicalRecordLogic(MedicalRecordMapper mapper, MedicalRecordRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    public MedicalRecord getById(String id) {
        return mapper.map(repository.findById(id).get());
    }

    public MedicalRecord getByRelation(String relation) {
           return null; //todo
    }

    public MedicalRecord save(MedicalRecord medicalRecord) {
        if (medicalRecord.relation() != null && medicalRecord.id() != null) {
            throw new IllegalStateException("Records with relations should not be updated directly");
        }

        return mapper.map(
            repository.save(mapper.map(medicalRecord))
        );
    }

    public void delete(String id) {
        repository.deleteById(id);
    }
}
