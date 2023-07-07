package org.goafabric.core.fhir.logic;

import org.goafabric.core.fhir.controller.vo.Patient;
import org.goafabric.core.fhir.logic.mapper.FPatientMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component(value = "FhirPatientLogic")
public class PatientLogic implements FhirLogic<Patient> {

    @Autowired
    private org.goafabric.core.data.logic.PatientLogic logic;
    
    @Autowired
    private FPatientMapper mapper;

    @Override
    public void create(Patient patient) {
        logic.save(mapper.map(patient));
    }

    @Override
    public void delete(String id) {
        logic.deleteById(id);
    }

    @Override
    public Patient getById(String id) {
        return mapper.map(logic.getById(id));
    }

    @Override
    public List<Patient> search(String search) {
        return mapper.map(logic.findByFamilyName(search));
    }
}
