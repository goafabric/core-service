package org.goafabric.core.fhir.logic;

import org.goafabric.core.fhir.controller.vo.Practitioner;
import org.goafabric.core.fhir.logic.mapper.FPractitionerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component(value = "FhirPractitionerLogic")
public class PractitionerLogic implements FhirLogic<Practitioner> {

    @Autowired
    private org.goafabric.core.data.logic.PractitionerLogic logic;
    
    @Autowired
    private FPractitionerMapper mapper;

    @Override
    public void create(Practitioner practitioner) {
        logic.save(mapper.map(practitioner));
    }

    @Override
    public void delete(String id) {
        logic.deleteById(id);
    }

    @Override
    public Practitioner getById(String id) {
        return mapper.map(logic.getById(id));
    }

    @Override
    public List<Practitioner> search(String search) {
        return mapper.map(logic.findByFamilyName(search));
    }
}
