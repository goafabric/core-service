package org.goafabric.core.ui.adapter;

import org.goafabric.core.medicalrecords.controller.dto.Encounter;
import org.goafabric.core.medicalrecords.controller.dto.MedicalRecordType;
import org.goafabric.core.medicalrecords.logic.jpa.EncounterLogic;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EncounterAdapter {
    private final EncounterLogic encounterLogic;

    public EncounterAdapter(EncounterLogic encounterLogic) {
        this.encounterLogic = encounterLogic;
    }

    public List<Encounter> findByPatientIdAndDisplayAndType(String patientId, String display, List<MedicalRecordType> types) {
        return encounterLogic.findByPatientIdAndDisplayAndType(patientId, display, types);
    }
}
