package org.goafabric.core.medicalrecords.logic;

import org.goafabric.core.medicalrecords.controller.dto.Encounter;

import java.util.List;

public interface EncounterLogicAble {
    Encounter save(Encounter encounter);

    List<Encounter> findByPatientIdAndDisplay(String patientId, String text);

    void delete (String id);
}
