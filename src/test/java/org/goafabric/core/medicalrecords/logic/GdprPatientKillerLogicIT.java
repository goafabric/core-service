package org.goafabric.core.medicalrecords.logic;

import org.goafabric.core.medicalrecords.logic.jpa.EncounterLogic;
import org.goafabric.core.medicalrecords.logic.jpa.MedicalRecordLogic;
import org.goafabric.core.medicalrecords.repository.EncounterImporter;
import org.goafabric.core.organization.logic.PatientLogic;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@SpringBootTest
class GdprPatientKillerLogicIT {

    @Autowired
    private EncounterImporter encounterImporter;

    @Autowired
    private GdprPatientKillerLogic patientKillerLogic;

    @Autowired
    private PatientLogic patientLogic;

    @Autowired
    private EncounterLogic encounterLogic;

    @Autowired
    private MedicalRecordLogic medicalRecordLogic;

    @Test
    void killEmAll() {
        encounterImporter.importDemoData();

        var patient = patientLogic.findByGivenName("Monty").getFirst();
        var encounters = encounterLogic.findByPatientIdAndDisplay(patient.id(), "");

        assertThat(encounters).isNotEmpty();

        patientKillerLogic.killemAll(patient.id());

        var encounters2 = encounterLogic.findByPatientIdAndDisplay(patient.id(), "");

        assertThat(encounters2).isEmpty();

        assertThatThrownBy(() ->
                medicalRecordLogic.getById(encounters.getFirst().medicalRecords().getFirst().id())).isInstanceOf(NoSuchElementException.class);

    }
}