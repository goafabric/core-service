package org.goafabric.core.medicalrecords.logic.jpa;

import jakarta.transaction.Transactional;
import org.goafabric.core.medicalrecords.controller.dto.BodyMetrics;
import org.goafabric.core.medicalrecords.controller.dto.MedicalRecord;
import org.goafabric.core.medicalrecords.controller.dto.MedicalRecordType;
import org.goafabric.core.medicalrecords.logic.MedicalRecordLogicAble;
import org.goafabric.core.medicalrecords.logic.mapper.BodyMetricsMapper;
import org.goafabric.core.medicalrecords.repository.jpa.BodyMetricsRepository;
import org.springframework.stereotype.Component;

@Component
@Transactional
public class BodyMetricsLogic {

    private final BodyMetricsMapper mapper;

    private final BodyMetricsRepository repository;

    private final MedicalRecordLogicAble medicalRecordLogic;

    public BodyMetricsLogic(BodyMetricsMapper mapper, BodyMetricsRepository repository, MedicalRecordLogicAble medicalRecordLogic) {
        this.mapper = mapper;
        this.repository = repository;
        this.medicalRecordLogic = medicalRecordLogic;
    }

    public BodyMetrics getById(String id) {
        return mapper.map(repository.findById(id).get());
    }

    public MedicalRecord save(BodyMetrics bodyMetrics) {
        var newBodyMetrics = mapper.map(
                repository.save(mapper.map(bodyMetrics)));
        return medicalRecordLogic.save(new MedicalRecord(null, null, null, MedicalRecordType.BODY_METRICS, newBodyMetrics.toDisplay(), "", newBodyMetrics.id()));
    }
}
