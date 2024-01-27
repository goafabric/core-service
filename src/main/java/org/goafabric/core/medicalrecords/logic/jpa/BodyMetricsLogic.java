package org.goafabric.core.medicalrecords.logic.jpa;

import jakarta.transaction.Transactional;
import org.goafabric.core.medicalrecords.controller.dto.BodyMetrics;
import org.goafabric.core.medicalrecords.controller.dto.MedicalRecord;
import org.goafabric.core.medicalrecords.controller.dto.MedicalRecordType;
import org.goafabric.core.medicalrecords.logic.mapper.BodyMetricsMapper;
import org.goafabric.core.medicalrecords.repository.jpa.BodyMetricsRepository;
import org.springframework.stereotype.Component;

@Component
@Transactional
public class BodyMetricsLogic {

    private final BodyMetricsMapper mapper;

    private final BodyMetricsRepository repository;

    public BodyMetricsLogic(BodyMetricsMapper mapper, BodyMetricsRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    public BodyMetrics getById(String id) {
        return mapper.map(repository.findById(id).get());
    }

    public MedicalRecord save(BodyMetrics bodyMetrics) {
        var newBodyMetrics = mapper.map(
                repository.save(mapper.map(bodyMetrics)));
        return new MedicalRecord(null, null, MedicalRecordType.BODY_METRICS, newBodyMetrics.toDisplay(), "", newBodyMetrics.id());
    }
}
