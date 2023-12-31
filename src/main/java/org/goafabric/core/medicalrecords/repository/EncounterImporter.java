package org.goafabric.core.medicalrecords.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.goafabric.core.organization.logic.PatientLogic;
import org.goafabric.core.medicalrecords.controller.dto.BodyMetrics;
import org.goafabric.core.medicalrecords.controller.dto.Encounter;
import org.goafabric.core.medicalrecords.controller.dto.MedicalRecord;
import org.goafabric.core.medicalrecords.controller.dto.MedicalRecordType;
import org.goafabric.core.medicalrecords.logic.BodyMetricsLogic;
import org.goafabric.core.medicalrecords.logic.EncounterLogic;
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

import static org.goafabric.core.organization.repository.extensions.DemoDataImporter.*;

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
        Arrays.asList(tenants.split(",")).forEach(tenant -> {
            setTenantId(tenant);
            if (applicationContext.getBean(PatientLogic.class).findByGivenName("Monty").isEmpty()) {
                insertData();
            }
        });
    }

    private void insertData() {
        insertObservations();
    }

    private void insertObservations() {
        var patient = patientLogic.save(
                createPatient("Monty", "Burns",
                        createAddress("Springfield"),
                        createContactPoint("555-520")));

        String practitionerId = null;

        //TODO: we currently need to manually synchronize body metrics data and display and record , should be one save that takes care of both
        var bodyMetrics = applicationContext.getBean(BodyMetricsLogic.class).save(
                new BodyMetrics(null, null, "170 cm", "100 cm", "30 cm", "30 %"));

        var medicalRecords = Arrays.asList(
                new MedicalRecord(MedicalRecordType.ANAMNESIS, "shows the tendency to eat a lot of sweets with sugar", ""),
                new MedicalRecord(null, null, MedicalRecordType.BODY_METRICS, bodyMetrics.toDisplay(), "", bodyMetrics.id()),
                new MedicalRecord(MedicalRecordType.FINDING,  "possible indication of Diabetes", ""),
                new MedicalRecord(MedicalRecordType.CONDITION, "Diabetes mellitus Typ 1", "none"),
                new MedicalRecord(MedicalRecordType.ANAMNESIS, "shows the behaviour to eat a lot of fast food with fat", ""),
                new MedicalRecord(MedicalRecordType.FINDING,  "clear indication of Adipositas", ""),
                new MedicalRecord(MedicalRecordType.CONDITION, "Adipositas", "E66.00"),
                new MedicalRecord(MedicalRecordType.ANAMNESIS, "hears strange voices of Üter Zörker, who tells him to set a fire", ""),
                new MedicalRecord(MedicalRecordType.FINDING,  "psychological disorder", ""),
                new MedicalRecord(MedicalRecordType.CONDITION, "Pyromanie", "F63.1"),
                new MedicalRecord(MedicalRecordType.CHARGEITEM, "normal examination", "GOÄ1"),
                new MedicalRecord(MedicalRecordType.THERAPY, "We recommend a sugar and fat free diet", "")
        );

        var stackedRecords = new ArrayList<MedicalRecord>();
        IntStream.range(0, 1).forEach(i -> stackedRecords.addAll(medicalRecords));

        IntStream.range(0, 1).forEach(i -> {
            var encounter = new Encounter(
                    null,
                    null,
                    patient.id(),
                    practitionerId,
                    LocalDate.now(),
                    "Encounter " + i,
                    stackedRecords
            );
            encounterLogic.save(encounter);
        });
    }

    public static void setTenantId(String tenantId) {
        SecurityContextHolder.getContext().setAuthentication(
                new OAuth2AuthenticationToken(new DefaultOAuth2User(new ArrayList<>(), new HashMap<>() {{ put("name", "import");}}, "name")
                        , new ArrayList<>(), tenantId));
    }


    private String writeObject(Object value) {
        try {
            return new ObjectMapper().writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
