package org.goafabric.core.medicalrecords.controller;

import org.goafabric.core.DataRocker;
import org.goafabric.core.extensions.HttpInterceptor;
import org.goafabric.core.medicalrecords.controller.dto.Encounter;
import org.goafabric.core.medicalrecords.controller.dto.MedicalRecord;
import org.goafabric.core.medicalrecords.controller.dto.MedicalRecordType;
import org.goafabric.core.medicalrecords.logic.MedicalRecordLogicAble;
import org.goafabric.core.medicalrecords.repository.jpa.EncounterRepository;
import org.goafabric.core.organization.controller.PatientController;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.goafabric.core.DataRocker.createAddress;
import static org.goafabric.core.DataRocker.createContactPoint;

@SpringBootTest
class EncounterControllerIT {
    @Autowired
    private EncounterController encounterController;

    @Autowired
    private PatientController patientController;

    @Autowired
    private MedicalRecordLogicAble medicalRecordLogic;

    @Autowired
    private EncounterRepository encounterRepository;

    @Test
    void findByPatientIdAndDisplay() {
        var patientId = createPatient();

        createEncounter(patientId);
        createEncounter(patientId);

        var encounters = encounterController.findByPatientIdAndDisplay(patientId, "Adipositas");

        assertThat(encounters).isNotNull().hasSize(2);
        assertThat(encounters.get(0).medicalRecords()).isNotNull().hasSize(2);
        assertThat(encounters.get(1).medicalRecords()).isNotNull().hasSize(2);

        deletePatient(patientId);
    }

    @NotNull
    private void createEncounter(String patientId) {
        String practitionerId = null;
        var medicalRecords = Arrays.asList(
                medicalRecordLogic.save(new MedicalRecord(MedicalRecordType.CONDITION, "Adipositas", "E66.00")),
                medicalRecordLogic.save(new MedicalRecord(MedicalRecordType.CONDITION, "Adipositas", "E66.00"))
        );
        var encounter = new Encounter(
                null,
                null,
                patientId,
                practitionerId,
                LocalDate.now(),
                "Encounter Test",
                medicalRecords
        );
        encounterController.save(encounter);
    }

    private String createPatient() {
        return patientController.save(
                DataRocker.createPatient("Homer", "Simpson",
                        createAddress("Evergreen Terrace " + HttpInterceptor.getTenantId()),
                        createContactPoint("555-444"))
        ).id();
    }

    private void deletePatient(String id) {
        patientController.deleteById(id);
    }
}