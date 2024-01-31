package org.goafabric.core.medicalrecords.logic;

import org.goafabric.core.medicalrecords.controller.dto.MedicalRecord;
import org.goafabric.core.medicalrecords.controller.dto.MedicalRecordType;

public interface MedicalRecordLogicAble {
    MedicalRecord getById(String id);

    MedicalRecord getByRelation(String relation);

    MedicalRecord save(MedicalRecord medicalRecord);

    MedicalRecord saveRelatedRecord(String relation, String existingId, MedicalRecordType type, String display, String code);

    void delete(String id);
}
