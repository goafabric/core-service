package org.goafabric.core.logic;

import org.goafabric.core.data.controller.dto.Address;
import org.goafabric.core.data.controller.dto.Patient;
import org.goafabric.core.data.logic.PatientLogic;
import org.goafabric.core.data.persistence.PatientRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PatientLogicIT {
    @Autowired
    private PatientLogic patientLogic;

    @Autowired
    private PatientRepository patientRepository;

    @Test
    public void findById() {
        List<Patient> patients = patientLogic.findAll();
        assertThat(patients).isNotNull().hasSize(3);

        final Patient patient
                = patientLogic.getById(patients.get(0).id());
        assertThat(patient).isNotNull();
        assertThat(patient.firstName()).isEqualTo(patients.get(0).firstName());
        assertThat(patient.lastName()).isEqualTo(patients.get(0).lastName());
    }

    @Test
    public void findAll() {
        assertThat(patientLogic.findAll()).isNotNull().hasSize(3);

        assertThat(patientLogic.findAll()).isNotNull().hasSize(3);
    }

    @Test
    public void findByFirstName() {
        List<Patient> patients = patientLogic.findByFirstName("Monty");
        assertThat(patients).isNotNull().hasSize(1);
        assertThat(patients.get(0).firstName()).isEqualTo("Monty");
        assertThat(patients.get(0).lastName()).isEqualTo("Burns");
    }

    @Test
    public void findByLastName() {
        List<Patient> patients = patientLogic.findByLastName("Simpson");
        assertThat(patients).isNotNull().hasSize(2);
        assertThat(patients.get(0).lastName()).isEqualTo("Simpson");
    }

    @Test
    void save() {
        final Patient patient = patientLogic.save(
            new Patient("null",
                    "Homer",
                    "Simpson",
                    createAddress("Evergreen Terrace")
        ));

        assertThat(patient).isNotNull();

        patientRepository.deleteById(patient.id());
    }

    private List<Address> createAddress(String street) {
        return Collections.singletonList(new Address(null,
                street, "Springfield"));
    }

}