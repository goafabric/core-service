package org.goafabric.core.fhir.r4.controller;

import ca.uhn.fhir.rest.client.api.IGenericClient;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Organization;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OrganizationFhirProjectorIT {
    @LocalServerPort
    private String port;

    @Test
    void findAndGet() {
        final IGenericClient client = ClientFactory.createClient(port);

        final Bundle bundle =
                client.search()
                        .forResource(Organization.class)
                        .where(Organization.NAME.matches().value(""))
                        .returnBundle(Bundle.class)
                        .execute();
        assertThat(bundle).isNotNull();
    }

}