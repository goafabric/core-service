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
class OrganizationControllerIT {
    @Autowired
    private OrganizationController controller;

    @Test
    public void getById() {
        setTenantId("0");
        var id = create();
        var organization = controller.getById(id);

        assertThat(organization).isNotNull();
        assertThat(organization.name()).isEqualTo("Practice Dr. Monroe");

        assertThat(organization.address()).isNotNull().isNotEmpty();
        assertThat(organization.address().get(0).city()).isEqualTo("Springfield");
        assertThat(organization.address().get(0).street()).isEqualTo("Psych Street 0");

        assertThat(organization.contactPoint()).isNotNull().isNotEmpty();
        assertThat(organization.contactPoint()).isNotNull().isNotEmpty();
        assertThat(organization.contactPoint().get(0).use()).isEqualTo(AddressUse.HOME.getValue());
        assertThat(organization.contactPoint().get(0).system()).isEqualTo(ContactPointSystem.PHONE.getValue());
        assertThat(organization.contactPoint().get(0).value()).isEqualTo("555-222");


        delete(id);
        assertThatThrownBy(() -> controller.getById(id)).isInstanceOf(Exception.class);
    }

    @Test
    public void findByGivenName() {
        setTenantId("0");
        var id0 = create();
        assertThat(controller.findByName("Practice Dr. Monroe")).isNotNull().hasSize(1);

        setTenantId("5");
        var id5 = create();
        assertThat(controller.findByName("Practice Dr. Monroe")).isNotNull().hasSize(1);

        delete(id0, "0");
        delete(id5, "5");
    }

    private String create() {
        return controller.save(
                createOrganization("Practice Dr. Monroe",
                        createAddress("Psych Street " + UserContext.getTenantId()),
                        createContactPoint("555-222"))
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