package org.goafabric.core.mrc.repository.extensions;

import org.goafabric.core.data.logic.PatientLogic;
import org.goafabric.core.mrc.controller.vo.Anamnesis;
import org.goafabric.core.mrc.controller.vo.Condition;
import org.goafabric.core.mrc.controller.vo.Encounter;
import org.goafabric.core.mrc.logic.EncounterLogic;
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
import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.IntStream;

import static org.goafabric.core.data.repository.extensions.DatabaseProvisioning.*;

@Component
public class EncounterImporter implements CommandLineRunner {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final String goals;

    private final Integer demoDataSize;

    private final String tenants;

    private final ApplicationContext applicationContext;

    @Autowired
    private EncounterLogic encounterLogic;

    @Autowired
    private PatientLogic patientLogic;

    public EncounterImporter(@Value("${database.provisioning.goals:}")String goals, @Value("${demo-data.size}") Integer demoDataSize, @Value("${multi-tenancy.tenants}") String tenants,
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

    public void importDemoData() {
        setTenantId("0");
        //if (applicationContext.getBean(PatientLogic.class).findByGivenName("Monty").isEmpty()) {
            insertData();
        //}
    }

    private void insertData() {
        insertObservations();
    }

    private void insertObservations() {
        var patient = patientLogic.save(
                createPatient("Monty", "Burns",
                        createAddress("Springfield"),
                        createContactPoint("555-520")));

        var anamnesis1 = new Anamnesis(null, "shows the tendency to eat a lot of sweets with sugar");
        var anamnesis2 = new Anamnesis(null, "shows the behaviour to eat a lot of fast food with fat");
        var anamnesis3 = new Anamnesis(null, "hears strange voices of Üter Zörker, that tell him to set a fire");

        var condition1 = new Condition(null, "none", "Diabetes mellitus Typ 1", "dm1");
        var condition2 = new Condition(null, "E66.00", "Adipositas", "adi");
        var condition3 = new Condition(null, "F63.1", "Pyromanie", "psy");

        IntStream.range(0, demoDataSize).forEach(i -> {
            var encounter = new Encounter(
                    null,
                    patient.id(),
                    LocalDate.now(),
                    Arrays.asList(anamnesis1, anamnesis2, anamnesis3),
                    Arrays.asList(condition1, condition2, condition3)
            );

            encounterLogic.save(encounter);
        });
    }

    public static void setTenantId(String tenantId) {
        SecurityContextHolder.getContext().setAuthentication(
                new OAuth2AuthenticationToken(new DefaultOAuth2User(new ArrayList<>(), new HashMap<>() {{ put("name", "import");}}, "name")
                        , new ArrayList<>(), tenantId));
    }


}
