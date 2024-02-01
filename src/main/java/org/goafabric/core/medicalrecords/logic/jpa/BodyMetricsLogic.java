package org.goafabric.core.medicalrecords.logic.jpa;

import jakarta.transaction.Transactional;
import org.goafabric.core.medicalrecords.controller.dto.BodyMetrics;
import org.goafabric.core.medicalrecords.controller.dto.MedicalRecord;
import org.goafabric.core.medicalrecords.logic.MedicalRecordLogic;
import org.goafabric.core.medicalrecords.logic.RecordDeleteAble;
import org.goafabric.core.medicalrecords.logic.jpa.mapper.BodyMetricsMapper;
import org.goafabric.core.medicalrecords.repository.jpa.BodyMetricsRepository;
import org.springframework.stereotype.Component;

@Component
@Transactional
public class BodyMetricsLogic implements RecordDeleteAble {

    private final BodyMetricsMapper mapper;

    private final BodyMetricsRepository repository;

    private final MedicalRecordLogic medicalRecordLogic;

    public BodyMetricsLogic(BodyMetricsMapper mapper, BodyMetricsRepository repository, MedicalRecordLogic medicalRecordLogic) {
        this.mapper = mapper;
        this.repository = repository;
        this.medicalRecordLogic = medicalRecordLogic;
    }

    public BodyMetrics getById(String id) {
        return mapper.map(repository.findById(id).get());
    }

    public MedicalRecord save(BodyMetrics bodyMetrics) {
        return medicalRecordLogic.saveRelatedRecord(
                repository.save(mapper.map(bodyMetrics)).getId(), bodyMetrics);
    }

    @Override
    public void delete(String id) {
        repository.deleteById(id);
    }
}
