package org.goafabric.core.importexport.logic;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.goafabric.core.organization.controller.dto.Organization;
import org.goafabric.core.organization.controller.dto.Patient;
import org.goafabric.core.organization.controller.dto.Practitioner;
import org.goafabric.core.organization.logic.OrganizationLogic;
import org.goafabric.core.organization.logic.PatientLogic;
import org.goafabric.core.organization.logic.PractitionerLogic;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Component
public class ImportLogic {
    private final PatientLogic patientLogic;
    private final PractitionerLogic practitionerLogic;
    private final OrganizationLogic organizationLogic;

    public ImportLogic(PatientLogic patientLogic, PractitionerLogic practitionerLogic, OrganizationLogic organizationLogic) {
        this.patientLogic = patientLogic;
        this.practitionerLogic = practitionerLogic;
        this.organizationLogic = organizationLogic;
    }

    public void run(String path) {
        if (!Files.exists(Paths.get(path))) {
            throw new IllegalStateException("Path not available");
        }

        try {
            importPatients(path);
            importPractitioners(path);
            importOrganizations(path);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private void importOrganizations(String path) throws IOException {
        var organizations = getObjectMapper().registerModule(new JavaTimeModule()).readValue(new File(path + "/organization.json"), new TypeReference<List<Organization>>() {});
        organizations.forEach(organizationLogic::save);
    }

    private void importPractitioners(String path) throws IOException {
        var practitioners = getObjectMapper().readValue(new File(path + "/practitioner.json"), new TypeReference<List<Practitioner>>() {});
        practitioners.forEach(practitionerLogic::save);
    }

    private void importPatients(String path) throws IOException {
        var patients = getObjectMapper().registerModule(new JavaTimeModule()).readValue(new File(path + "/patient.json"), new TypeReference<List<Patient>>() {});
        patients.forEach(patientLogic::save);
    }

    private ObjectMapper getObjectMapper() {
        return new ObjectMapper().registerModule(new JavaTimeModule()).configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

}
