package org.goafabric.core.medicalrecords.logic;

import org.goafabric.core.medicalrecords.controller.dto.MedicalRecord;
import org.goafabric.core.medicalrecords.controller.dto.MedicalRecordAble;

public interface MedicalRecordLogic {
    MedicalRecord getById(String id);

    MedicalRecord save(MedicalRecord medicalRecord);

    MedicalRecord saveRelatedRecord(String specialization, MedicalRecordAble medicalRecordAble);

    void delete(String id);
}
