package org.goafabric.core.ui.adapter;

import org.goafabric.core.organization.controller.dto.Patient;
import org.goafabric.core.organization.logic.PatientLogic;
import org.goafabric.core.organization.repository.entity.PatientNamesOnly;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PatientAdapter implements SearchAdapter<Patient> {
    private final PatientLogic patientLogic;

    public PatientAdapter(PatientLogic patientLogic) {
        this.patientLogic = patientLogic;
    }

    @Override
    public List<Patient> search(String search) {
        return patientLogic.findByFamilyName(search);
    }

    public List<PatientNamesOnly> findPatientNamesByFamilyName(String familyName, String givenName) {
        return patientLogic.findByFamilyNameAndGivenName(familyName, givenName);
    }

    public void save(Patient patient) {
        patientLogic.save(patient);
    }

    public void delete(String id) {
        patientLogic.deleteById(id);
    }
}
