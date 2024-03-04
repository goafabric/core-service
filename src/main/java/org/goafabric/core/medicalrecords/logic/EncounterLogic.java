package org.goafabric.core.medicalrecords.logic;

import org.goafabric.core.medicalrecords.controller.dto.Encounter;
import org.goafabric.core.medicalrecords.controller.dto.MedicalRecordType;

import java.util.List;

public interface EncounterLogic {
    Encounter save(Encounter encounter);

    List<Encounter> findByPatientIdAndDisplay(String patientId, String text);

    List<Encounter> findByPatientIdAndDisplayAndType(String patientId, String text, List<MedicalRecordType> types);

    void delete (String id);

    void deleteAllByPatientId(String patientId);
}
