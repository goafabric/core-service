package org.goafabric.core.ui.adapter;

import org.goafabric.core.data.controller.vo.Organization;
import org.goafabric.core.data.logic.OrganizationLogic;
import org.goafabric.core.ui.SearchLogic;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrganizationAdapter implements SearchLogic<Organization> {
    private final OrganizationLogic practitionerLogic;

    public OrganizationAdapter(OrganizationLogic practitionerLogic) {
        this.practitionerLogic = practitionerLogic;
    }

    @Override
    public List<Organization> search(String search) {
        return practitionerLogic.findByName(search);
    }

}
