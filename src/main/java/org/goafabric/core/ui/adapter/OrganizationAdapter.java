package org.goafabric.core.ui.adapter;

import org.goafabric.core.organization.controller.OrganizationController;
import org.goafabric.core.organization.controller.vo.Organization;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrganizationAdapter implements SearchAdapter<Organization> {
    private final OrganizationController organizationLogic;

    public OrganizationAdapter(OrganizationController practitionerLogic) {
        this.organizationLogic = practitionerLogic;
    }

    @Override
    public List<Organization> search(String search) {
        return organizationLogic.findByName(search);
    }

    public void save(Organization organization) {
        organizationLogic.save(organization);
    }

    public void delete(String id) {
        organizationLogic.deleteById(id);
    }
}
