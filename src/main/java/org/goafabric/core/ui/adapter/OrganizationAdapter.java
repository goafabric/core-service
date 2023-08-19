package org.goafabric.core.ui.adapter;

import org.goafabric.core.data.controller.vo.Organization;
import org.goafabric.core.data.logic.OrganizationLogic;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrganizationAdapter implements SearchAdapter<Organization> {
    private final OrganizationLogic organizationLogic;

    public OrganizationAdapter(OrganizationLogic practitionerLogic) {
        this.organizationLogic = practitionerLogic;
    }

    @Override
    public List<Organization> search(String search) {
        return organizationLogic.findByName(search);
    }

    public Organization save(Organization organization) {
        return organizationLogic.save(organization);
    }
}
