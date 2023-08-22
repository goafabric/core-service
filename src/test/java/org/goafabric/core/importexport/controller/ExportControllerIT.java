package org.goafabric.core.importexport.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.goafabric.core.organization.controller.PatientController;
import org.goafabric.core.organization.controller.vo.Patient;
import org.goafabric.core.extensions.HttpInterceptor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.goafabric.core.DataRocker.*;

@SpringBootTest
class ExportControllerIT {
    @Autowired
    private PatientController patientController;

    @Autowired
    private ExportController exportController;

    @Autowired
    private ImportController importController;

    @Test
    void export() throws IOException {
        setTenantId("0");
        var id = create();

        var tempDir = System.getProperty("java.io.tmpdir");

        exportController.run(tempDir);
        importController.run(tempDir);

        var patients = new ObjectMapper().registerModule(new JavaTimeModule()).configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .registerModule(new JavaTimeModule()).readValue(new File(tempDir + "/patient.json"), new TypeReference<List<Patient>>() {});

        assertThat(patients).isNotNull().isNotEmpty();
        delete(id);
    }

    private String create() {
        return patientController.save(
                createPatient("Homer", "Simpson",
                        createAddress("Evergreen Terrace " + HttpInterceptor.getTenantId()),
                        createContactPoint("555-444"))
        ).id();
    }

    private void delete(String id) {
        patientController.deleteById(id);
    }


}