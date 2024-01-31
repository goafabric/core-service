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
        var newId = mapper.map(repository.save(mapper.map(bodyMetrics))).id();
        return updateMedicalRecord(newId, bodyMetrics.id(), MedicalRecordType.BODY_METRICS, bodyMetrics.toDisplay(), "");
    }

    public MedicalRecord updateMedicalRecord(String relation, String existingId, MedicalRecordType type, String display, String code) {
        if (existingId != null) {
            var medicalRecord = medicalRecordLogic.getByRelation(existingId);
            return medicalRecordLogic.save(new MedicalRecord(medicalRecord.id(), medicalRecord.encounterId(), medicalRecord.version(),
                    type, display, medicalRecord.code(), medicalRecord.relation()));
        } else {
            return medicalRecordLogic.save(new MedicalRecord(null, null, null, type, display, code, relation));
        }
    }

    public void delete(BodyMetrics bodyMetrics) {
        repository.deleteById(bodyMetrics.id());
        var medicalRecord = medicalRecordLogic.getByRelation(bodyMetrics.id());
        medicalRecordLogic.delete(medicalRecord.id());
    }
}
