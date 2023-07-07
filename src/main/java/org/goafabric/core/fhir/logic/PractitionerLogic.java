package org.goafabric.core.fhir.logic;

import org.goafabric.core.fhir.controller.vo.Practitioner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component(value = "PractitionerLogic")
public class PractitionerLogic implements FhirLogic<Practitioner> {
    @Override
    public void create(Practitioner patient) {
        
    }

    @Override
    public void delete(String id) {

    }

    @Override
    public Practitioner getById(String id) {
        return null;
    }

    @Override
    public List<Practitioner> search(String search) {
        return null;
    }
}
