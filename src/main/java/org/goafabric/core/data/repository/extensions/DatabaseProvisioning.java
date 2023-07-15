package org.goafabric.core.data.repository.extensions;

import net.datafaker.Faker;
import org.goafabric.core.data.controller.vo.*;
import org.goafabric.core.data.controller.vo.types.AddressUse;
import org.goafabric.core.data.controller.vo.types.ContactPointSystem;
import org.goafabric.core.data.logic.OrganizationLogic;
import org.goafabric.core.data.logic.PatientLogic;
import org.goafabric.core.data.logic.PractitionerLogic;
import org.goafabric.core.data.extensions.HttpInterceptor;
import org.goafabric.core.data.controller.vo.ObjectEntry;
import org.goafabric.core.data.logic.ObjectStorageLogic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ImportRuntimeHints;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.IntStream;

@Component
@ImportRuntimeHints(DatabaseProvisioning.DbRuntimeHints.class)
public class DatabaseProvisioning implements CommandLineRunner {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final String goals;

    private final Integer demoDataSize;

    private final String tenants;

    private final ApplicationContext applicationContext;

    public DatabaseProvisioning(@Value("${database.provisioning.goals:}")String goals, @Value("${demo-data.size}") Integer demoDataSize, @Value("${multi-tenancy.tenants}") String tenants,
                                ApplicationContext applicationContext) {
        this.goals = goals;
        this.demoDataSize = demoDataSize;
        this.tenants = tenants;
        this.applicationContext = applicationContext;
    }

    @Override
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
        Arrays.asList(tenants.split(",")).forEach(tenant -> {
            setTenantId(tenant);
            if (applicationContext.getBean(PatientLogic.class).findByFamilyName("").isEmpty()) {
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
                createPractitioner("Dr. Julius", "Hibbert",
                        createAddress("Commonstreet 345"),
                        createContactPoint("555-520")
                )
        );

        applicationContext.getBean(PractitionerLogic.class).save(
                createPractitioner("Dr. Marvin", "Monroe",
                        createAddress("Psychstreet 104"),
                        createContactPoint("555-525")
                )
        );

        applicationContext.getBean(PractitionerLogic.class).save(
                createPractitioner("Dr. Nick", "Riveria",
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
        return Collections.singletonList(
                new Address(null, AddressUse.HOME.getValue(),street, "Springfield " + HttpInterceptor.getTenantId()
                        , "555", "Florida", "US"));
    }

    public static List<ContactPoint> createContactPoint(String phone) {
        return Collections.singletonList(new ContactPoint(null, AddressUse.HOME.getValue(), ContactPointSystem.PHONE.getValue(), phone));
    }


    @Autowired(required = false)
    private ObjectStorageLogic objectStorageLogic;

    private void createArchiveFiles() {
        try {
            if (objectStorageLogic != null) {
                objectStorageLogic.create(
                        new ObjectEntry("hello_world.txt", "text/plain",
                                Long.valueOf("hello world".length()), "hello world".getBytes()));

                objectStorageLogic.create(
                        new ObjectEntry("top_secret.txt", "text/plain",
                                Long.valueOf("top secret".length()), "top secret".getBytes()));
            }
        } catch (Exception e) { //to have low coupling it's ok to not have demodate if s3 is not started
            log.warn("Could not S3 Demo Data: " + e.getMessage());
        }
    }


    public static void setTenantId(String tenantId) {
        SecurityContextHolder.getContext().setAuthentication(
                new OAuth2AuthenticationToken(new DefaultOAuth2User(new ArrayList<>(), new HashMap<>() {{ put("name", "import");}}, "name")
                        , new ArrayList<>(), tenantId));
    }

    static class DbRuntimeHints implements RuntimeHintsRegistrar {
        @Override
        public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
            hints.resources().registerPattern("en/*.yml"); //needed for stupid faker
        }
    }

}
