package org.goafabric.core.fhir.logic;

import org.goafabric.core.fhir.controller.vo.Organization;
import org.springframework.stereotype.Component;

import java.util.List;

@Component(value = "FhirOrganizationLogic")
public class OrganizationLogic implements FhirLogic<Organization> {
    @Override
    public void create(Organization patient) {
        
    }

    @Override
    public void delete(String id) {

    }

    @Override
    public Organization getById(String id) {
        return null;
    }

    @Override
    public List<Organization> search(String search) {
        return null;
    }
}
