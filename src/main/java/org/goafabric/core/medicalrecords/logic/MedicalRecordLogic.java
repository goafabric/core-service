package org.goafabric.core.medicalrecords.logic;

import jakarta.transaction.Transactional;
import org.goafabric.core.medicalrecords.controller.dto.MedicalRecord;
import org.goafabric.core.medicalrecords.logic.mapper.MedicalRecordMapper;
import org.goafabric.core.medicalrecords.repository.MedicalRecordRepository;
import org.springframework.stereotype.Component;

@Component
@Transactional
public class MedicalRecordLogic {

    private final MedicalRecordMapper mapper;

    private final MedicalRecordRepository repository;

    public MedicalRecordLogic(MedicalRecordMapper mapper, MedicalRecordRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    public MedicalRecord getById(String id) {
        return mapper.map(repository.findById(id).get());
    }

    public MedicalRecord save(MedicalRecord medicalRecord) {
        return mapper.map(
            repository.save(mapper.map(medicalRecord))
        );
    }
}
