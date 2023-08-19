package org.goafabric.core.mrc.controller;

import org.goafabric.core.DataRocker;
import org.goafabric.core.data.controller.PatientController;
import org.goafabric.core.extensions.HttpInterceptor;
import org.goafabric.core.mrc.controller.vo.Encounter;
import org.goafabric.core.mrc.controller.vo.MedicalRecord;
import org.goafabric.core.mrc.controller.vo.MedicalRecordType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.goafabric.core.DataRocker.*;

@SpringBootTest
class EncounterControllerIT {
    @Autowired
    private EncounterController encounterController;

    @Autowired
    private PatientController patientController;

    @Test
    void findByPatientIdAndDisplay() {
        var patientId = createPatient();

        var encounter = new Encounter(
                null,
                null,
                patientId,
                LocalDate.now(),
                "Encounter Test",
                Collections.singletonList(
                        new MedicalRecord(MedicalRecordType.CONDITION, "Adipositas", "E66.00"))
        );
        encounterController.save(encounter);
        
        var sut = encounterController.findByPatientIdAndDisplay(patientId, "Adipositas");

        assertThat(sut).isNotNull().hasSize(1);
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