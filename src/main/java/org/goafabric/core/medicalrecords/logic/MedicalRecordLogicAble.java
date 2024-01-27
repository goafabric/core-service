package org.goafabric.core.medicalrecords.logic;

import org.goafabric.core.medicalrecords.controller.dto.MedicalRecord;

public interface MedicalRecordLogicAble {
    MedicalRecord getById(String id);

    MedicalRecord save(MedicalRecord medicalRecord);
}