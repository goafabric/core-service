package org.goafabric.core.fhir.logic;

import org.goafabric.core.fhir.controller.vo.Patient;
import org.springframework.stereotype.Component;

import java.util.List;

@Component(value = "FhirPatientLogic")
public class PatientLogic implements FhirLogic<Patient> {
    @Override
    public void create(Patient patient) {

    }

    @Override
    public void delete(String id) {

    }

    @Override
    public Patient getById(String id) {
        return null;
    }

    @Override
    public List<Patient> search(String search) {
        return null;
    }
}
