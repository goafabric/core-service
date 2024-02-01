package org.goafabric.core.medicalrecords.logic;

import org.goafabric.core.medicalrecords.controller.dto.MedicalRecord;
import org.goafabric.core.medicalrecords.controller.dto.RecordAble;

public interface MedicalRecordLogic {
    MedicalRecord getById(String id);

    MedicalRecord save(MedicalRecord medicalRecord);

    MedicalRecord saveRelatedRecord(String relation, RecordAble recordAble);

    void delete(String id);
}
