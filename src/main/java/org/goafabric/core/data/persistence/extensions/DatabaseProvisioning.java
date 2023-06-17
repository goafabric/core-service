package org.goafabric.core.data.persistence.extensions;

import org.goafabric.core.data.controller.dto.Address;
import org.goafabric.core.data.controller.dto.ContactPoint;
import org.goafabric.core.data.controller.dto.Patient;
import org.goafabric.core.data.controller.dto.types.AdressUse;
import org.goafabric.core.data.controller.dto.types.ContactPointSystem;
import org.goafabric.core.data.crossfunctional.HttpInterceptor;
import org.goafabric.core.data.logic.PatientLogic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Component
public class DatabaseProvisioning {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Value("${database.provisioning.goals:}")
    private String goals;

    @Autowired
    private PatientLogic patientLogic;

    @Autowired
    private ApplicationContext applicationContext;

    //@Override
    public void run(String... args) {
        if ((args.length > 0) && ("-check-integrity".equals(args[0]))) { return; }

        if (goals.contains("-import-demo-data")) {
            log.info("Importing demo data ...");
            importDemoData();
            log.info("Demo data import done ...");
        }

        if (goals.contains("-terminate")) {
            log.info("Terminating app ...");
            SpringApplication.exit(applicationContext, () -> 0); //if an exception is raised, spring will automatically terminate with 1
        }
    }

    private void importDemoData() {
        HttpInterceptor.setTenantId("0");
        if (patientLogic.findAll().isEmpty()) {
            HttpInterceptor.setTenantId("0");
            insertData();
            HttpInterceptor.setTenantId("5");
            insertData();
        }
    }

    private void insertData() {
        patientLogic.save(
                createPatient("Homer", "Simpson",
                        createAddress("Evergreen Terrace 1"),
                        createContactPoint())
        );

        patientLogic.save(
                createPatient("Bart", "Simpson",
                        createAddress("Everblue Terrace 1"),
                        createContactPoint())
        );

        patientLogic.save(
                createPatient("Monty", "Burns",
                        createAddress("Monty Mansion"),
                        createContactPoint())
        );


    }

    private Patient createPatient(String givenName, String familyName, List<Address> addresses, List<ContactPoint> contactPoints) {
        return new Patient(null, givenName, familyName, "male", LocalDate.of(2020, 1, 8),
                addresses, contactPoints
        );
    }

    private List<Address> createAddress(String street) {
        return Collections.singletonList(new Address(null, AdressUse.HOME.getValue(),"Evergreen Terrace", "Springfield " + HttpInterceptor.getTenantId()));
    }

    private List<ContactPoint> createContactPoint() {
        return Collections.singletonList(new ContactPoint(null, AdressUse.HOME.getValue(), ContactPointSystem.PHONE.getValue(), "5555-44444"));
    }

    /*
    private static void setTenantId(String tenantId) {
        SecurityContextHolder.getContext().setAuthentication(
                new OAuth2AuthenticationToken(new DefaultOAuth2User(new ArrayList<>(), new HashMap<>() {{ put("name", "");}}, "name")
                        , new ArrayList<>(), tenantId));
    }

     */


}
