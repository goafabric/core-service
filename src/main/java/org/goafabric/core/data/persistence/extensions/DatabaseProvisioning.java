package org.goafabric.core.data.persistence.extensions;

import net.datafaker.Faker;
import org.goafabric.core.crossfunctional.HttpInterceptor;
import org.goafabric.core.data.controller.dto.*;
import org.goafabric.core.data.controller.dto.types.AdressUse;
import org.goafabric.core.data.controller.dto.types.ContactPointSystem;
import org.goafabric.core.data.logic.OrganizationLogic;
import org.goafabric.core.data.logic.PatientLogic;
import org.goafabric.core.data.logic.PractitionerLogic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.IntStream;

@Component
public class DatabaseProvisioning implements CommandLineRunner {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Value("${database.provisioning.goals:}")
    String goals;

    @Value("${demo-data.size}")
    Integer demoDataSize;

    private final PatientLogic patientLogic;

    private final PractitionerLogic practitionerLogic;

    private final OrganizationLogic organizationLogic;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private Runnable schemaCreator;

    public DatabaseProvisioning(PatientLogic patientLogic, PractitionerLogic practitionerLogic, OrganizationLogic organizationLogic) {
        this.patientLogic = patientLogic;
        this.practitionerLogic = practitionerLogic;
        this.organizationLogic = organizationLogic;
    }

    @Override
    public void run(String... args) {
        if ((args.length > 0) && ("-check-integrity".equals(args[0]))) { return; }

        schemaCreator.run();

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
        setTenantId("0");
        if (patientLogic.findAll().isEmpty()) {
            setTenantId("0");
            insertData();
            setTenantId("5");
            insertData();
        }
    }

    private void insertData() {
        createPatients();
        createPractitioners();
        createOrganizations();
    }

    private void createPatients() {
        Faker faker = new Faker();
        IntStream.range(0, demoDataSize).forEach(i ->
            patientLogic.save(
                    createPatient(faker.name().firstName(), faker.name().lastName(),
                            createAddress(faker.simpsons().location()),
                            createContactPoint("555-520"))
            )
        );
    }

    private void createPractitioners() {
        practitionerLogic.save(
                createPractitioner("Dr Julius", "Hibbert",
                        createAddress("Commonstreet 345"),
                        createContactPoint("555-520")
                )
        );

        practitionerLogic.save(
                createPractitioner("Dr Marvin", "Monroe",
                        createAddress("Psychstreet 104"),
                        createContactPoint("555-525")
                )
        );

        practitionerLogic.save(
                createPractitioner("Dr Nick", "Riveria",
                        createAddress("Nickstreet 221"),
                        createContactPoint("555-501")
                )
        );
    }

    private void createOrganizations() {
        organizationLogic.save(
                createOrganization("Practice Dr Hibbert",
                        createAddress("Hibbertstreet 4"),
                        createContactPoint("555-501")
                )
        );

        organizationLogic.save(
                createOrganization("Practice Dr Nick",
                        createAddress("Nickstreet 54"),
                        createContactPoint("555-501")
                )
        );
    }

    public static Patient createPatient(String givenName, String familyName, List<Address> addresses, List<ContactPoint> contactPoints) {
        return new Patient(null, givenName, familyName, "male", LocalDate.of(2020, 1, 8),
                addresses, contactPoints
        );
    }

    public static Practitioner createPractitioner(String givenName, String familyName, List<Address> addresses, List<ContactPoint> contactPoints) {
        return new Practitioner(null, givenName, familyName, "male", LocalDate.of(2020, 1, 8),
                addresses, contactPoints
        );
    }

    public static Organization createOrganization(String name, List<Address> addresses, List<ContactPoint> contactPoints) {
        return new Organization(null, name, addresses, contactPoints);
    }

    public static List<Address> createAddress(String street) {
        return Collections.singletonList(new Address(null, AdressUse.HOME.getValue(),street, "Springfield " + HttpInterceptor.getTenantId()));
    }

    public static List<ContactPoint> createContactPoint(String phone) {
        return Collections.singletonList(new ContactPoint(null, AdressUse.HOME.getValue(), ContactPointSystem.PHONE.getValue(), phone));
    }

    /*
    private void setTenantId(String tenantId) {
        HttpInterceptor.setTenantId("0");
    }
     */

    private static void setTenantId(String tenantId) {
        SecurityContextHolder.getContext().setAuthentication(
                new OAuth2AuthenticationToken(new DefaultOAuth2User(new ArrayList<>(), new HashMap<>() {{ put("name", "import");}}, "name")
                        , new ArrayList<>(), tenantId));
    }

}
