package org.goafabric.core.mrc.logic;

import org.goafabric.core.mrc.controller.vo.Encounter;

import java.util.List;

public interface EncounterLogic {
    void save(Encounter encounter);

    List<Encounter> findByPatientIdAndText(String patientId, String text);
}
