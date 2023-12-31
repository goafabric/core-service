package org.goafabric.core.medicalrecords.logic;

import org.goafabric.core.medicalrecords.repository.EncounterImporter;
import org.goafabric.core.organization.logic.PatientLogic;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.goafabric.core.DataRocker.setTenantId;

@SpringBootTest
class EncounterLogicIT {

    @Autowired
    private EncounterLogic encounterLogic;

    @Autowired
    private PatientLogic patientLogic;

    @Autowired
    private EncounterImporter encounterImporter;


    @Test
    public void findByText() {
        setTenantId("0");
        encounterImporter.importDemoData();

        long currentTime = System.currentTimeMillis();

        var patient = patientLogic.findByGivenName("Monty").get(0);
        var encounters = encounterLogic.findByPatientIdAndDisplay(patient.id(), "eat");

        System.out.println("search took: "  + (System.currentTimeMillis() -currentTime));

        assertThat(encounters).isNotEmpty();
        assertThat(encounters.get(0).medicalRecords()).isNotNull().isNotEmpty();

        var lst = encounters.get(0).medicalRecords();
        lst.stream().forEach(anamnesis -> System.out.println(anamnesis.display()));

    }

}

