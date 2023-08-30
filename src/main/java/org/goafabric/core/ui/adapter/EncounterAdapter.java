package org.goafabric.core.ui.adapter;

import org.goafabric.core.medicalrecords.controller.EncounterController;
import org.goafabric.core.medicalrecords.controller.vo.Encounter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EncounterAdapter {
    private final EncounterController encounterLogic;

    public EncounterAdapter(EncounterController encounterLogic) {
        this.encounterLogic = encounterLogic;
    }

    public List<Encounter> findByPatientIdAndDisplay(String patientId, String display) {
        return encounterLogic.findByPatientIdAndDisplay(patientId, display);
    }
}
