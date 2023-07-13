package org.goafabric.core.fhir.r4.controller;

import ca.uhn.fhir.rest.client.api.IGenericClient;
import org.goafabric.core.data.controller.PractitionerController;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.IdType;
import org.hl7.fhir.r4.model.Patient;
import org.hl7.fhir.r4.model.Practitioner;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;
import static org.goafabric.core.DataRocker.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PractitionerFhirProjectorIT {
    @LocalServerPort
    private String port;

    @Test
    void search() {
        var id = create();
        final IGenericClient client = ClientFactory.createClient(port);

        final Bundle bundle =
                client.search()
                        .forResource(Practitioner.class)
                        .where(Patient.FAMILY.matches().value("Monroe"))
                        .returnBundle(Bundle.class)
                        .execute();
        assertThat(bundle).isNotNull();

        var practitioner  = (Practitioner) bundle.getEntry().get(0).getResource();
        //assertThat(patient.getName().get(0).getFamily()).isEqualTo("Simpson");
        //assertThat(patient.getName().get(0).getGiven().get(0).toString()).isEqualTo("Homer");

        assertThat(practitioner.getAddress()).hasSize(1);
        var address = practitioner.getAddress().get(0);
        assertThat(address.getCity()).isEqualTo("Springfield");
        assertThat(address.getPostalCode()).isEqualTo("555");
        assertThat(address.getCountry()).isEqualTo("US");

        assertThat(address.getUse().toCode()).isEqualTo("home");

        assertThat(address.getLine()).hasSize(1);
        assertThat(address.getLine().get(0).toString()).isEqualTo("Monroe Street");

        assertThat(practitioner.getTelecom()).hasSize(1);
        var contactPoint = practitioner.getTelecom().get(0);
        assertThat(contactPoint.getValue()).isEqualTo("555-333");
        assertThat(contactPoint.getUse().toCode()).isEqualTo("home");
        assertThat(contactPoint.getSystem().toCode()).isEqualTo("phone");

        delete(id);
    }

    @Autowired
    private PractitionerController controller;

    private String create() {
        return controller.save(
                createPractitioner("Marvin", "Monroe",
                        createAddress("Monroe Street"),
                        createContactPoint("555-333"))
        ).id();
    }

    private void delete(String id) {
        controller.deleteById(id);
        final IGenericClient client = ClientFactory.createClient(port);
        client.delete().resourceById(new IdType("Practitioner", id)).execute();
    }
}