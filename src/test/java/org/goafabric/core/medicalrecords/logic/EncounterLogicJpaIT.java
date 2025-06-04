package org.goafabric.core.medicalrecords.logic;

import org.goafabric.core.medicalrecords.persistence.EncounterImporter;
import org.goafabric.core.organization.logic.PatientLogic;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.goafabric.core.DataRocker.setTenantId;

@SpringBootTest
class EncounterLogicJpaIT {

    @Autowired
    private EncounterLogic encounterLogic;

    @Autowired
    private PatientLogic patientLogic;

    @Autowired
    private EncounterImporter encounterImporter;


    @Test
    void findByText() {
        setTenantId("0");
        encounterImporter.importDemoData();

        long currentTime = System.currentTimeMillis();

        var patient = patientLogic.findByGivenName("Monty").get(0);
        var encounters = encounterLogic.findByPatientIdAndDisplay(patient.id(), "eat");

        System.out.println("search took: "  + (System.currentTimeMillis() -currentTime));

        assertThat(encounters).isNotEmpty();
        assertThat(encounters.getFirst().medicalRecords()).isNotNull().isNotEmpty();

        var lst = encounters.getFirst().medicalRecords();
        lst.forEach(anamnesis -> System.out.println(anamnesis.display()));
    }

}

