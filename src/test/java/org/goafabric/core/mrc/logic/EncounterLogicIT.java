package org.goafabric.core.mrc.logic;

import org.goafabric.core.data.logic.PatientLogic;
import org.goafabric.core.mrc.repository.extensions.EncounterImporter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EncounterLogicIT {

    @Autowired
    private EncounterLogic encounterLogic;

    @Autowired
    private PatientLogic patientLogic;

    @Autowired
    private EncounterImporter encounterImporter;

    @Value("${spring.profiles.active}")
    private String profile;

    @BeforeAll
    public static void init() {

    }

    @Test
    public void findByText() {
        encounterImporter.importDemoData();

        long currentTime = System.currentTimeMillis();

        var patient = patientLogic.findByGivenName("Homer").get(0);
        var encounters = encounterLogic.findByPatientIdAndText(patient.id(), "eat");

        System.out.println("search took: "  + (System.currentTimeMillis() -currentTime));

        assertThat(encounters).isNotEmpty();
        assertThat(encounters.get(0).anamnesises()).isNotNull().isNotEmpty();

        var lst = encounters.get(0).anamnesises();
        lst.stream().forEach(anamnesis -> System.out.println(anamnesis.text()));

    }

}

