package org.goafabric.core.logic;

import org.goafabric.core.data.controller.dto.Patient;
import org.goafabric.core.crossfunctional.HttpInterceptor;
import org.goafabric.core.data.logic.PatientLogic;
import org.goafabric.core.data.persistence.PatientRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.goafabric.core.data.persistence.extensions.DatabaseProvisioning.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PatientLogicIT {
    @Autowired
    private PatientLogic patientLogic;

    @Autowired
    private PatientRepository patientRepository;

    @BeforeAll
    public void init() {
        insertData();
    }

    @Test
    public void findById() {
        HttpInterceptor.setTenantId("0");

        List<Patient> patients = patientLogic.findAll();
        assertThat(patients).isNotNull().hasSize(3);

        final Patient patient
                = patientLogic.getById(patients.get(0).id());
        assertThat(patient).isNotNull();
        assertThat(patient.givenName()).isEqualTo(patients.get(0).givenName());
        assertThat(patient.familyName()).isEqualTo(patients.get(0).familyName());

        assertThat(patient.contactPoint()).isNotNull().hasSize(1);
        assertThat(patient.address()).isNotNull().hasSize(1);
    }

    @Test
    public void findAll() {
        HttpInterceptor.setTenantId("0");
        assertThat(patientLogic.findAll()).isNotNull().hasSize(3);

        HttpInterceptor.setTenantId("5");
        assertThat(patientLogic.findAll()).isNotNull().hasSize(3);
    }

    @Test
    public void findBygivenName() {
        List<Patient> patients = patientLogic.findByGivenName("Monty");
        assertThat(patients).isNotNull().hasSize(1);
        assertThat(patients.get(0).givenName()).isEqualTo("Monty");
        assertThat(patients.get(0).familyName()).isEqualTo("Burns");
    }

    @Test
    public void findByfamilyName() {
        List<Patient> patients = patientLogic.findByFamilyName("Simpson");
        assertThat(patients).isNotNull().hasSize(2);
        assertThat(patients.get(0).familyName()).isEqualTo("Simpson");
    }


    private void insertData() {
        HttpInterceptor.setTenantId("0");
        createPatients();
        HttpInterceptor.setTenantId("5");
        createPatients();
    }

    private void createPatients() {
        patientLogic.save(
                createPatient("Homer", "Simpson",
                        createAddress("Evergreen Terrace 1"),
                        createContactPoint())
        );

        patientLogic.save(
                createPatient("Bart", "Simpson",
                        createAddress("Everblue Terrace 1"),
                        createContactPoint())
        );

        patientLogic.save(
                createPatient("Monty", "Burns",
                        createAddress("Monty Mansion"),
                        createContactPoint())
        );
    }




}