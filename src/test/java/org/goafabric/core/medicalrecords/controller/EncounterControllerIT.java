package org.goafabric.core.medicalrecords.controller;

import org.goafabric.core.DataRocker;
import org.goafabric.core.extensions.HttpInterceptor;
import org.goafabric.core.medicalrecords.controller.dto.Encounter;
import org.goafabric.core.medicalrecords.controller.dto.MedicalRecord;
import org.goafabric.core.medicalrecords.controller.dto.MedicalRecordType;
import org.goafabric.core.organization.controller.PatientController;
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

    @Test
    void findByPatientIdAndDisplay() {
        var patientId = createPatient();
        String practitionerId = null;

        var encounter = new Encounter(
                null,
                null,
                patientId,
                practitionerId,
                LocalDate.now(),
                "Encounter Test",
                Arrays.asList(
                        new MedicalRecord(MedicalRecordType.CONDITION, "Adipositas", "E66.00"),
                        new MedicalRecord(MedicalRecordType.CONDITION, "Adipositas", "E66.00")
                )
        );
        encounterController.save(encounter);
        encounterController.save(encounter);

        var encounters = encounterController.findByPatientIdAndDisplay(patientId, "Adipositas");

        assertThat(encounters).isNotNull().hasSize(2);
        assertThat(encounters.get(0).medicalRecords()).isNotNull().hasSize(2);
        assertThat(encounters.get(1).medicalRecords()).isNotNull().hasSize(2);

        deletePatient(patientId);
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