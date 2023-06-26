package org.goafabric.core.data.persistence.extensions;

import net.datafaker.Faker;
import org.goafabric.core.crossfunctional.HttpInterceptor;
import org.goafabric.core.data.controller.dto.*;
import org.goafabric.core.data.controller.dto.types.AdressUse;
import org.goafabric.core.data.controller.dto.types.ContactPointSystem;
import org.goafabric.core.data.logic.OrganizationLogic;
import org.goafabric.core.data.logic.PatientLogic;
import org.goafabric.core.data.logic.PractitionerLogic;
import org.goafabric.core.files.objectstorage.dto.ObjectEntry;
import org.goafabric.core.files.objectstorage.logic.ObjectStorageLogic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.IntStream;

@Component
public class DatabaseProvisioning implements CommandLineRunner {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final String goals;

    private final Integer demoDataSize;

    private final String tenants;

    private final ApplicationContext applicationContext;

    private final Runnable schemaCreator;

    public DatabaseProvisioning(@Value("${database.provisioning.goals:}")String goals, @Value("${demo-data.size}") Integer demoDataSize, @Value("${multi-tenancy.tenants}") String tenants,
                                ApplicationContext applicationContext, Runnable schemaCreator) {
        this.goals = goals;
        this.demoDataSize = demoDataSize;
        this.tenants = tenants;
        this.applicationContext = applicationContext;
        this.schemaCreator = schemaCreator;
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
        Arrays.asList(tenants.split(",")).forEach(tenant -> {
            setTenantId(tenant);
            if (applicationContext.getBean(PatientLogic.class).findAll().isEmpty()) {
                insertData();
            }
        });
    }

    private void insertData() {
        createPatients();
        createPractitioners();
        createOrganizations();
        createArchiveFiles();
    }

    private void createPatients() {
        Faker faker = new Faker();
        IntStream.range(0, demoDataSize).forEach(i ->
            applicationContext.getBean(PatientLogic.class).save(
                    createPatient(faker.name().firstName(), faker.name().lastName(),
                            createAddress(faker.simpsons().location()),
                            createContactPoint("555-520"))
            )
        );
    }

    private void createPractitioners() {
        applicationContext.getBean(PractitionerLogic.class).save(
                createPractitioner("Dr Julius", "Hibbert",
                        createAddress("Commonstreet 345"),
                        createContactPoint("555-520")
                )
        );

        applicationContext.getBean(PractitionerLogic.class).save(
                createPractitioner("Dr Marvin", "Monroe",
                        createAddress("Psychstreet 104"),
                        createContactPoint("555-525")
                )
        );

        applicationContext.getBean(PractitionerLogic.class).save(
                createPractitioner("Dr Nick", "Riveria",
                        createAddress("Nickstreet 221"),
                        createContactPoint("555-501")
                )
        );
    }

    private void createOrganizations() {
        applicationContext.getBean(OrganizationLogic.class).save(
                createOrganization("Practice Dr Hibbert",
                        createAddress("Hibbertstreet 4"),
                        createContactPoint("555-501")
                )
        );

        applicationContext.getBean(OrganizationLogic.class).save(
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

    @Value("${spring.cloud.aws.s3.enabled}") Boolean s3Enabled;
    private void createArchiveFiles() {
        if (s3Enabled) {
            applicationContext.getBean(ObjectStorageLogic.class).create(
                    new ObjectEntry("hello_world.txt", "text/plain",
                            Long.valueOf("hello world".length()), "hello world".getBytes()));

            applicationContext.getBean(ObjectStorageLogic.class).create(
                    new ObjectEntry("top_secret.txt", "text/plain",
                            Long.valueOf("top secret".length()), "top secret".getBytes()));
        }
    }


    private static void setTenantId(String tenantId) {
        SecurityContextHolder.getContext().setAuthentication(
                new OAuth2AuthenticationToken(new DefaultOAuth2User(new ArrayList<>(), new HashMap<>() {{ put("name", "import");}}, "name")
                        , new ArrayList<>(), tenantId));
    }

}
