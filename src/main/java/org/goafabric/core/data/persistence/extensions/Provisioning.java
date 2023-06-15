package org.goafabric.core.data.persistence.extensions;

import org.goafabric.core.data.controller.dto.Address;
import org.goafabric.core.data.controller.dto.Patient;
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
public class Provisioning {
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
        patientLogic.save(new Patient(null, "Homer", "Simpson", "male", LocalDate.of(2020, 1, 8)
                        , createAddress("Evergreen Terrace 1")));

        patientLogic.save(new Patient(null, "Bart", "Simpson", "male", LocalDate.of(2020, 1, 8)
                , createAddress("Everblue Terrace 1")));

        patientLogic.save(new Patient(null, "Monty", "Burns", "male", LocalDate.of(2020, 1, 8)
                , createAddress("Monty Mansion")));

    }

    private List<Address> createAddress(String street) {
        return Collections.singletonList(new Address(null, "Evergreen Terrace", "Springfield " + HttpInterceptor.getTenantId()));
    }

}
