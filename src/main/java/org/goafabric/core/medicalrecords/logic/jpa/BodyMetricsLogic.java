package org.goafabric.core.medicalrecords.logic.jpa;

import jakarta.transaction.Transactional;
import org.goafabric.core.medicalrecords.controller.dto.BodyMetrics;
import org.goafabric.core.medicalrecords.controller.dto.MedicalRecord;
import org.goafabric.core.medicalrecords.logic.MedicalRecordLogic;
import org.goafabric.core.medicalrecords.logic.MedicalRecordDeleteAble;
import org.goafabric.core.medicalrecords.logic.jpa.mapper.BodyMetricsMapper;
import org.goafabric.core.medicalrecords.persistence.jpa.BodyMetricsRepository;
import org.springframework.stereotype.Component;

@Component
@Transactional
public class BodyMetricsLogic implements MedicalRecordDeleteAble {

    private final BodyMetricsMapper mapper;

    private final BodyMetricsRepository repository;

    private final MedicalRecordLogic medicalRecordLogic;

    public BodyMetricsLogic(BodyMetricsMapper mapper, BodyMetricsRepository repository, MedicalRecordLogic medicalRecordLogic) {
        this.mapper = mapper;
        this.repository = repository;
        this.medicalRecordLogic = medicalRecordLogic;
    }

    public BodyMetrics getById(String id) {
        return mapper.map(repository.findById(id).orElseThrow());
    }

    //saves or updates the specific body metrics, as well as the generic related medical_record for search
    public MedicalRecord save(BodyMetrics bodyMetrics) {
        return medicalRecordLogic.saveSpecializedRecord(
                repository.save(mapper.map(bodyMetrics)).getId(), bodyMetrics);
    }

    //only deletes it's own data, will be called upon medical record deletion, not exposed via controller
    @Override
    public void delete(String id) {
        repository.deleteById(id);
    }
}
