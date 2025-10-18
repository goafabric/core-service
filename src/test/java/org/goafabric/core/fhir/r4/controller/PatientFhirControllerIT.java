package org.goafabric.core.fhir.r4.controller;

import ca.uhn.fhir.rest.client.api.IGenericClient;
import org.goafabric.core.organization.controller.PatientController;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Patient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.test.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;
import static org.goafabric.core.DataRocker.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PatientFhirControllerIT {
    @LocalServerPort
    private String port;

    @Test
    void search() {
        var id = create();
        final IGenericClient client = ClientFactory.createClient(port);

        final Bundle bundle =
                client.search()
                        .forResource(Patient.class)
                        .where(Patient.FAMILY.matches().value("Simpson"))
                        .returnBundle(Bundle.class)
                        .execute();

        assertThat(bundle).isNotNull();
        var patient = (Patient) bundle.getEntry().getFirst().getResource();

        assertThat(patient.getName()).hasSize(1);
        assertThat(patient.getName().getFirst().getGiven().getFirst()).hasToString("Homer");
        assertThat(patient.getName().getFirst().getFamily()).isEqualTo("Simpson");

        assertThat(patient.getAddress()).hasSize(1);
        var address = patient.getAddress().getFirst();
        assertThat(address.getCity()).isEqualTo("Springfield");
        assertThat(address.getPostalCode()).isEqualTo("555");
        assertThat(address.getCountry()).isEqualTo("US");

        assertThat(address.getUse().toCode()).isEqualTo("home");

        assertThat(address.getLine()).hasSize(1);
        assertThat(address.getLine().getFirst()).hasToString("Evergreen Terrace");

        assertThat(patient.getTelecom()).hasSize(1);
        var contactPoint = patient.getTelecom().getFirst();
        assertThat(contactPoint.getValue()).isEqualTo("555-444");
        assertThat(contactPoint.getUse().toCode()).isEqualTo("home");
        assertThat(contactPoint.getSystem().toCode()).isEqualTo("phone");

        assertThat(client.read().resource(Patient.class).withId(id).execute()).isNotNull();

        delete(id);
    }
    

    @Autowired
    private PatientController controller;


    private String create() {
        return controller.save(
                createPatient("Homer", "Simpson",
                        createAddress("Evergreen Terrace"),
                        createContactPoint("555-444"))
        ).id();
    }

    private void delete(String id) {
        controller.deleteById(id);
    }

}