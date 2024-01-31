package org.goafabric.core.medicalrecords.logic;

import org.goafabric.core.organization.logic.PatientLogic;
import org.springframework.stereotype.Component;

@Component
public class GdprPatientKillerLogic {
    private final PatientLogic patientLogic;
    private final EncounterLogicAble encounterLogic;
    private final MedicalRecordLogicAble medicalRecordLogic;

    public GdprPatientKillerLogic(PatientLogic patientLogic, EncounterLogicAble encounterLogic, MedicalRecordLogicAble medicalRecordLogic) {
        this.patientLogic = patientLogic;
        this.encounterLogic = encounterLogic;
        this.medicalRecordLogic = medicalRecordLogic;
    }

    public void killemAll(String patientId) {
        encounterLogic.findByPatientIdAndDisplay(patientId, "").forEach(encounter -> {
            encounter.medicalRecords().forEach(medicalRecord -> medicalRecordLogic.delete(medicalRecord.id()));
            encounterLogic.delete(encounter.id());
        });
        patientLogic.deleteById(patientId);
    }
}
