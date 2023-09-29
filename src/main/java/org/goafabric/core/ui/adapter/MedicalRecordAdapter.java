package org.goafabric.core.ui.adapter;


import org.goafabric.core.medicalrecords.controller.MedicalRecordController;
import org.goafabric.core.medicalrecords.controller.vo.MedicalRecord;
import org.springframework.stereotype.Component;

@Component
public class MedicalRecordAdapter {
    private final MedicalRecordController medicalRecordLogic;

    public MedicalRecordAdapter(MedicalRecordController medicalRecordLogic) {
        this.medicalRecordLogic = medicalRecordLogic;
    }

    public MedicalRecord getById(String id) {
        return medicalRecordLogic.getById(id);
    }

    public MedicalRecord save(MedicalRecord medicalRecord) {
        return medicalRecordLogic.save(medicalRecord);
    }
}
