package org.goafabric.core.data.controller;

import org.goafabric.core.data.extensions.HttpInterceptor;
import org.goafabric.core.data.controller.vo.types.AddressUse;
import org.goafabric.core.data.controller.vo.types.ContactPointSystem;
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
    private PatientController controller;

    @Test
    public void getById() {
        setTenantId("0");
        var id = create();
        var patient = controller.getById(id);

        assertThat(patient).isNotNull();
        assertThat(patient.givenName()).isEqualTo("Homer");
        assertThat(patient.familyName()).isEqualTo("Simpson");

        assertThat(patient.address()).isNotNull().isNotEmpty();
        assertThat(patient.address().get(0).city()).isEqualTo("Springfield");
        assertThat(patient.address().get(0).street()).isEqualTo("Evergreen Terrace 0");

        assertThat(patient.contactPoint()).isNotNull().isNotEmpty();
        assertThat(patient.contactPoint().get(0).use()).isEqualTo(AddressUse.HOME.getValue());
        assertThat(patient.contactPoint().get(0).system()).isEqualTo(ContactPointSystem.PHONE.getValue());
        assertThat(patient.contactPoint().get(0).value()).isEqualTo("555-444");

        delete(id);
        assertThatThrownBy(() -> controller.getById(id)).isInstanceOf(Exception.class);
    }

    @Test
    public void findByGivenName() {
        setTenantId("0");
        var id0 = create();
        assertThat(controller.findByGivenName("Homer")).isNotNull().hasSize(1);

        setTenantId("5");
        var id5 = create();
        assertThat(controller.findByGivenName("Homer")).isNotNull().hasSize(1);

        delete(id0, "0");
        delete(id5, "5");
    }

    @Test
    public void findByFamilyName() {
        setTenantId("0");
        var id0 = create();
        assertThat(controller.findByFamilyName("Simpson")).isNotNull().hasSize(1);

        setTenantId("5");
        var id5 = create();
        assertThat(controller.findByFamilyName("Simpson")).isNotNull().hasSize(1);

        delete(id0, "0");
        delete(id5, "5");
    }


    private String create() {
        return controller.save(
                createPatient("Homer", "Simpson",
                        createAddress("Evergreen Terrace " + HttpInterceptor.getTenantId()),
                        createContactPoint("555-444"))
        ).id();
    }

    private void delete(String id, String tenantId) {
        setTenantId(tenantId);
        delete(id);
    }

    private void delete(String id) {
        controller.deleteById(id);
    }

}