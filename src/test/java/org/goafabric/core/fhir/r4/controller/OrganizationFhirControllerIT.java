package org.goafabric.core.fhir.r4.controller;

import ca.uhn.fhir.rest.client.api.IGenericClient;
import org.goafabric.core.organization.controller.OrganizationController;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Organization;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.test.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;
import static org.goafabric.core.DataRocker.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OrganizationFhirControllerIT {
    @LocalServerPort
    private String port;

    @Test
    void search() {
        var id = create();

        final IGenericClient client = ClientFactory.createClient(port);

        final Bundle bundle =
                client.search()
                        .forResource(Organization.class)
                        .where(Organization.NAME.matches().value("Practice Dr. Monroe"))
                        .returnBundle(Bundle.class)
                        .execute();
        assertThat(bundle).isNotNull();

        var organization = (Organization) bundle.getEntry().getFirst().getResource();

        assertThat(organization.getName()).isEqualTo("Practice Dr. Monroe");

        assertThat(organization.getAddress()).hasSize(1);
        var address = organization.getAddress().getFirst();
        assertThat(address.getCity()).isEqualTo("Springfield");
        assertThat(address.getPostalCode()).isEqualTo("555");
        assertThat(address.getCountry()).isEqualTo("US");

        assertThat(address.getUse().toCode()).isEqualTo("home");

        assertThat(address.getLine()).hasSize(1);
        assertThat(address.getLine().getFirst()).hasToString("Psych Street");

        assertThat(organization.getTelecom()).hasSize(1);
        var contactPoint = organization.getTelecom().getFirst();
        assertThat(contactPoint.getValue()).isEqualTo("555-222");
        assertThat(contactPoint.getUse().toCode()).isEqualTo("home");
        assertThat(contactPoint.getSystem().toCode()).isEqualTo("phone");

        assertThat(client.read().resource(Organization.class).withId(id).execute()).isNotNull();

        delete(id);
    }

    @Autowired
    private OrganizationController controller;

    private String create() {
        return controller.save(
                createOrganization("Practice Dr. Monroe",
                        createAddress("Psych Street"),
                        createContactPoint("555-222"))
        ).id();
    }

    private void delete(String id) {
        controller.deleteById(id);
    }
}