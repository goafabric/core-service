package org.goafabric.core.logic;

import org.goafabric.core.data.controller.dto.Address;
import org.goafabric.core.data.controller.dto.Patient;
import org.goafabric.core.data.controller.dto.types.AdressUse;
import org.goafabric.core.data.logic.PatientLogic;
import org.goafabric.core.data.persistence.PatientRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
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
        assertThat(patient.givenName()).isEqualTo(patients.get(0).givenName());
        assertThat(patient.familyName()).isEqualTo(patients.get(0).familyName());
    }

    @Test
    public void findAll() {
        assertThat(patientLogic.findAll()).isNotNull().hasSize(3);

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

    @Test
    void save() {
        final Patient patient = patientLogic.save(
            new Patient("null",
                    "Homer",
                    "Simpson",
                     "male", LocalDate.of(2020, 1, 8),
                    createAddress("Evergreen Terrace")
        ));

        assertThat(patient).isNotNull();

        patientRepository.deleteById(patient.id());
    }

    private List<Address> createAddress(String street) {
        return Collections.singletonList(new Address(null, AdressUse.HOME.getValue(), street, "Springfield"));
    }

}