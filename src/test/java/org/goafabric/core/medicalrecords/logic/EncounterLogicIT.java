package org.goafabric.core.medicalrecords.logic;

import org.goafabric.core.medicalrecords.controller.dto.MedicalRecordType;
import org.goafabric.core.medicalrecords.logic.chatbot.BruteChatBot;
import org.goafabric.core.medicalrecords.repository.EncounterImporter;
import org.goafabric.core.organization.logic.PatientLogic;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.goafabric.core.DataRocker.setTenantId;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class EncounterLogicIT {

    @Autowired
    private EncounterLogic encounterLogic;

    @Autowired
    private PatientLogic patientLogic;

    @Autowired
    private EncounterImporter encounterImporter;

    @Autowired
    private BruteChatBot chatBot;

    @BeforeAll
    public void init() {
        setTenantId("0");
        encounterImporter.importDemoData();
    }

    @Test
    public void findByText() {
        long currentTime = System.currentTimeMillis();

        var patient = patientLogic.findByGivenName("Monty").get(0);
        var encounters = encounterLogic.findByPatientIdAndDisplay(patient.id(), "eat");

        System.out.println("search took: "  + (System.currentTimeMillis() -currentTime));

        assertThat(encounters).isNotEmpty();
        assertThat(encounters.getFirst().medicalRecords()).isNotNull().isNotEmpty();

        var lst = encounters.getFirst().medicalRecords();
        lst.forEach(anamnesis -> System.out.println(anamnesis.display()));
    }


    @Test
    public void createSearchResultNameAndType() {
        assertThat(chatBot.createSearchResult("I am searching all Diagnosis for Monty").patientName().getFullName()).isEqualTo("Monty Burns");
        assertThat(chatBot.createSearchResult("I am searching all Diagnosis and Anamnesis for Monty").medicalRecordTypes()).contains(MedicalRecordType.CONDITION);
        assertThat(chatBot.createSearchResult("I am searching all Diagnosis and Anamnesis for Monty").medicalRecordTypes()).contains(MedicalRecordType.ANAMNESIS);

        assertThat(chatBot.createSearchResult("").patientName()).isNull();
        assertThat(chatBot.createSearchResult("").medicalRecordTypes()).isEmpty();
    }

    @Test
    public void createSearchResultDisplayText() {
        assertThat(chatBot.createSearchResult("I am searching for all Diagnosis for Monty with text sugar and mice").displayText()).isEqualTo("sugar");
        assertThat(chatBot.createSearchResult("I am searching for all Diagnosis for Monty that contain sugar and mice").displayText()).isEqualTo("sugar");
        assertThat(chatBot.createSearchResult("I am searching for all Diagnosis for Monty that contain text sugar and mice").displayText()).isEqualTo("sugar");
    }

    @Test
    public void find() {
        var prevPatientId = patientLogic.findByGivenName("Monty").get(0).id();

        assertThat(chatBot.chat("hi", prevPatientId)).isEmpty();
        assertThat(chatBot.chat("I am searching for Monty", prevPatientId)).isNotEmpty();
        //assertThat(chatBot.chat("I am searching for all Diagnosis for Homer", prevPatientId)).isEmpty(); //Todo: Homer has no data attached

        assertThat(chatBot.chat("I am searching all Diagnosis and Anamnesis for Monty that contain nothing", prevPatientId)).isEmpty();
        assertThat(chatBot.chat("I am searching all Diagnosis and Anamnesis", prevPatientId)).isNotEmpty();

        var encounters1 = chatBot.chat("I am searching all Diagnosis and Anamnesis for Bart that contain sugar", prevPatientId);
        //medicalRecords1.stream().forEach(m -> System.out.println(m.toString()));
        assertThat(encounters1.getFirst().medicalRecords()).hasSize(1);

        assertThat(chatBot.chat("I am searching all Diagnosis and Anamnesis for Monty that contain", prevPatientId)).isNotEmpty();

        var encounters2 = chatBot.chat("I am searching all Diagnosis and Anamnesis for Burns", prevPatientId);
        //medicalRecords2.stream().forEach(m -> System.out.println(m.toString()));
        assertThat(encounters2.getFirst().medicalRecords()).hasSize(6);

    }

}

