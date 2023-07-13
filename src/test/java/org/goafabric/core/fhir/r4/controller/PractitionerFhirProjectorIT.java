package org.goafabric.core.fhir.r4.controller;

import ca.uhn.fhir.rest.client.api.IGenericClient;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Patient;
import org.hl7.fhir.r4.model.Practitioner;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PractitionerFhirProjectorIT {
    @LocalServerPort
    private String port;

    @Test
    void findAndGet() {
        final IGenericClient client = ClientFactory.createClient(port);

        final Bundle bundle =
                client.search()
                        .forResource(Practitioner.class)
                        .where(Patient.FAMILY.matches().value(""))
                        .returnBundle(Bundle.class)
                        .execute();
        assertThat(bundle).isNotNull();
    }

    /*
    @Test
    void findAndGetPractitioner() {
        final IGenericClient client = ClientFactory.createClient(port);

        final Bundle bundle =
                client.search()
                        .forResource(Practitioner.class)
                        .where(Patient.FAMILY.matches().value("Hibbert"))
                        .returnBundle(Bundle.class)
                        .execute();

        assertThat(bundle).isNotNull();

        var practitioner = (Practitioner) bundle.getEntry().get(0).getResource();
        assertThat(practitioner).isNotNull();
        assertThat(practitioner.getName()).hasSize(1);
        assertThat(practitioner.getName().get(0).getFamily()).isEqualTo("Hibbert");

        var practitioner2 =
                client.read()
                        .resource(Practitioner.class)
                        .withId(practitioner.getId()).execute();
        assertThat(practitioner2).isNotNull();
        assertThat(practitioner2.getName().get(0).getFamily()).isEqualTo("Hibbert");

    }



    @Test
    void create() {
        final IGenericClient client = ClientFactory.createClient(port);
        var practitioner = new Practitioner();
        practitioner.setName(Collections.singletonList(new HumanName().setFamily("none")));

        client.create().resource(practitioner).execute();
    }

    @Test
    void delete() {
        final IGenericClient client = ClientFactory.createClient(port);
        client.delete().resourceById(new IdType("Practitioner", "1")).execute();
    }

     */
}