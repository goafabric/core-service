package org.goafabric.core.fhir.logic;

import org.goafabric.core.fhir.controller.vo.Organization;
import org.goafabric.core.fhir.logic.mapper.FOrganizationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component(value = "FhirOrganizationLogic")
public class OrganizationLogic implements FhirLogic<Organization> {

    @Autowired
    private org.goafabric.core.data.logic.OrganizationLogic logic;

    @Autowired
    private FOrganizationMapper mapper;

    @Override
    public void create(Organization organization) {
        logic.save(mapper.map(organization));
    }

    @Override
    public void delete(String id) {
        logic.deleteById(id);
    }

    @Override
    public Organization getById(String id) {
        return mapper.map(logic.getById(id));
    }

    @Override
    public List<Organization> search(String search) {
        return mapper.map(logic.findByName(search));
    }
}
