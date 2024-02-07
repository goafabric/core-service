package org.goafabric.core.organization.controller;

import org.goafabric.core.extensions.HttpInterceptor;
import org.goafabric.core.organization.logic.PatientLogic;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.goafabric.core.DataRocker.*;


@SpringBootTest
class PatientControllerSearchIT {
    @Autowired
    private PatientController controller;

    @Autowired
    private PatientLogic patientLogic;

    @Test
    public void test() {
        setTenantId("0");
        var michael = create("Michael", "Meyers");
        var hans = create("Hans", "Müller");

        assertThat(findBy("Meyers", "")).isNotEmpty();
        assertThat(findBy("Meiers", "")).isNotEmpty();

        assertThat(findBy("Meyers", "Michael")).isNotEmpty();
        assertThat(findBy("Meiers", "Michael")).isNotEmpty();

        assertThat(findBy("Mey", "Mic")).isNotEmpty();

        assertThat(findBy("Müller", "Hans")).isNotEmpty();
        assertThat(findBy("Mueller", "Hans")).isNotEmpty();
        assertThat(findBy("Mueller", "Hanz")).isNotEmpty();

        assertThat(findBy("Müll", "Han")).isNotEmpty();

        /*
        assertThat(findBy("Mei", "")).isEmpty();
        assertThat(findBy("Muell", "")).isEmpty();

        assertThat(findBy("Hans", "Müller")).isEmpty();
        assertThat(findBy("Michael", "Meyers")).isEmpty();

         */

        delete(michael);
        delete(hans);
    }

    private String create(String givenName, String familyName) {
        return controller.save(
                createPatient(givenName, familyName,
                        createAddress("Evergreen Terrace " + HttpInterceptor.getTenantId()),
                        createContactPoint("555-444"))
        ).id();
    }

    private String findBy(String familyName, String givenName) {
        return patientLogic.findByFamilyNameAndGivenName(familyName, givenName).toString();
    }

    private void delete(String id, String tenantId) {
        setTenantId(tenantId);
        delete(id);
    }

    private void delete(String id) {
        controller.deleteById(id);
    }

}