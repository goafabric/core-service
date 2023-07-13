package org.goafabric.core.fhir.controller;

import ca.uhn.fhir.rest.client.api.IGenericClient;
import org.hl7.fhir.r4.model.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PatientFhirProjectorIT {
    @LocalServerPort
    private String port;

    @Test
    void findAndGet() {
        final IGenericClient client = ClientFactory.createClient(port);

        final Bundle bundle =
                client.search()
                        .forResource(Patient.class)
                        .where(Practitioner.FAMILY.matches().value(""))
                        .returnBundle(Bundle.class)
                        .execute();

        assertThat(bundle).isNotNull();
    }
    /*
    @Test
    void findAndGetPatient() {
        final IGenericClient client = ClientFactory.createClient(port);

        final Bundle bundle =
                client.search()
                        .forResource(Patient.class)
                        .where(Patient.FAMILY.matches().value("Simpson"))
                        .returnBundle(Bundle.class)
                        .execute();

        assertThat(bundle).isNotNull();
        final Patient patient = (Patient) bundle.getEntry().get(0).getResource();

        assertThat(patient.getName()).hasSize(1);
        assertThat(patient.getName().get(0).getFamily()).isEqualTo("Simpson");
        assertThat(patient.getName().get(0).getGiven().get(0).toString()).isEqualTo("Homer");

        assertThat(patient.getAddress()).hasSize(1);
        var address = patient.getAddress().get(0);
        assertThat(address.getCity()).isEqualTo("Springfield");
        assertThat(address.getPostalCode()).isEqualTo("78313");
        assertThat(address.getCountry()).isEqualTo("US");

        assertThat(address.getUse().toCode()).isEqualTo("home");

        assertThat(address.getLine()).hasSize(1);
        assertThat(address.getLine().get(0).toString()).isEqualTo("Evergreen Terrace 742");

        assertThat(patient.getTelecom()).hasSize(1);
        var contactPoint = patient.getTelecom().get(0);
        assertThat(contactPoint.getValue()).isEqualTo("0245-33553");
        assertThat(contactPoint.getUse().toCode()).isEqualTo("home");
        assertThat(contactPoint.getSystem().toCode()).isEqualTo("phone");

        var patient2 =
                client.read()
                        .resource(Patient.class)
                        .withId(patient.getId()).execute();
        assertThat(patient2).isNotNull();
        assertThat(patient2.getName().get(0).getFamily()).isEqualTo("Simpson");
    }

    @Test
    //@Disabled
    void create() {
        final IGenericClient client = ClientFactory.createClient(port);
        var patient = new Patient();
        patient.setName(Collections.singletonList(new HumanName().setFamily("none")));
        client.create().resource(patient).execute();
    }


    @Test
    void delete() {
        final IGenericClient client = ClientFactory.createClient(port);
        client.delete().resourceById(new IdType("Patient", "1")).execute();
    }


     */

}