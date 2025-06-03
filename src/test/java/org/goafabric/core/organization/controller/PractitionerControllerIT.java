package org.goafabric.core.organization.controller;

import org.goafabric.core.extensions.UserContext;
import org.goafabric.core.organization.controller.dto.types.AddressUse;
import org.goafabric.core.organization.controller.dto.types.ContactPointSystem;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.goafabric.core.DataRocker.*;


@SpringBootTest
class PractitionerControllerIT {
    @Autowired
    private PractitionerController controller;

    @Test
    public void getById() {
        setTenantId("0");
        var id = create();
        var practitioner = controller.getById(id);

        assertThat(practitioner).isNotNull();
        assertThat(practitioner.givenName()).isEqualTo("Marvin");
        assertThat(practitioner.familyName()).isEqualTo("Monroe");

        assertThat(practitioner.address()).isNotNull().isNotEmpty();
        assertThat(practitioner.address().get(0).city()).isEqualTo("Springfield");
        assertThat(practitioner.address().get(0).street()).isEqualTo("Monroe Street 0");

        assertThat(practitioner.contactPoint()).isNotNull().isNotEmpty();
        assertThat(practitioner.contactPoint()).isNotNull().isNotEmpty();
        assertThat(practitioner.contactPoint().get(0).use()).isEqualTo(AddressUse.HOME.getValue());
        assertThat(practitioner.contactPoint().get(0).system()).isEqualTo(ContactPointSystem.PHONE.getValue());
        assertThat(practitioner.contactPoint().get(0).value()).isEqualTo("555-333");


        delete(id);
        assertThatThrownBy(() -> controller.getById(id)).isInstanceOf(Exception.class);
    }

    @Test
    public void findByGivenName() {
        setTenantId("0");
        var id0 = create();
        assertThat(controller.findByGivenName("Marvin")).isNotNull().hasSize(1);

        setTenantId("5");
        var id5 = create();
        assertThat(controller.findByGivenName("Marvin")).isNotNull().hasSize(1);

        delete(id0, "0");
        delete(id5, "5");
    }

    @Test
    public void findByFamilyName() {
        setTenantId("0");
        var id0 = create();
        assertThat(controller.findByFamilyName("Monroe")).isNotNull().hasSize(1);

        setTenantId("5");
        var id5 = create();
        assertThat(controller.findByFamilyName("Monroe")).isNotNull().hasSize(1);

        delete(id0, "0");
        delete(id5, "5");
    }


    private String create() {
        return controller.save(
                createPractitioner("Marvin", "Monroe",
                        createAddress("Monroe Street " + UserContext.getTenantId()),
                        createContactPoint("555-333"))
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