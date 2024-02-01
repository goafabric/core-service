package org.goafabric.core.medicalrecords.logic;

import org.goafabric.core.medicalrecords.controller.dto.MedicalRecord;
import org.goafabric.core.medicalrecords.controller.dto.RecordAble;

public interface MedicalRecordLogicAble {
    MedicalRecord getById(String id);

    MedicalRecord save(MedicalRecord medicalRecord);

    public MedicalRecord saveRelatedRecord(String relation, RecordAble recordAble);

    void delete(String id);
}
