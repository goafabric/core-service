package org.goafabric.core.data.controller;

import org.goafabric.core.crossfunctional.TenantInterceptor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.goafabric.core.DataRocker.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PatientControllerIT {
    @Autowired
    private PatientController patientLogic;

    @Test
    public void getById() {
        setTenantId("0");
        var id = create();
        var patient = patientLogic.getById(id);

        assertThat(patient).isNotNull();
        assertThat(patient.givenName()).isEqualTo("Homer");
        assertThat(patient.familyName()).isEqualTo("Simpson");

        assertThat(patient.address()).isNotNull().isNotEmpty();
        assertThat(patient.address().get(0).city()).isEqualTo("Springfield");
        assertThat(patient.address().get(0).street()).isEqualTo("Evergreen Terrace 0");

        assertThat(patient.contactPoint()).isNotNull().isNotEmpty();

        delete(id);
        assertThatThrownBy(() -> patientLogic.getById(id)).isInstanceOf(Exception.class);
    }

    @Test
    public void findByGivenName() {
        setTenantId("0");
        var id0 = create();
        assertThat(patientLogic.findByGivenName("Homer")).isNotNull().hasSize(1);

        setTenantId("5");
        var id5 = create();
        assertThat(patientLogic.findByGivenName("Homer")).isNotNull().hasSize(1);

        delete(id0, "0");
        delete(id5, "5");
    }

    @Test
    public void findByFamilyName() {
        setTenantId("0");
        var id0 = create();
        assertThat(patientLogic.findByFamilyName("Simpson")).isNotNull().hasSize(1);

        setTenantId("5");
        var id5 = create();
        assertThat(patientLogic.findByFamilyName("Simpson")).isNotNull().hasSize(1);

        delete(id0, "0");
        delete(id5, "5");
    }


    private String create() {
        return patientLogic.save(
                createPatient("Homer", "Simpson",
                        createAddress("Evergreen Terrace " + TenantInterceptor.getTenantId()),
                        createContactPoint("555-444"))
        ).id();
    }

    private void delete(String id, String tenantId) {
        setTenantId(tenantId);
        delete(id);
    }

    private void delete(String id) {
        patientLogic.deleteById(id);
    }

}