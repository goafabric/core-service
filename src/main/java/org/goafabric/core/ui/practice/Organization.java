package org.goafabric.core.ui.practice;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.goafabric.core.organization.controller.dto.types.PermissionType;
import org.goafabric.core.ui.MainView;
import org.goafabric.core.ui.adapter.OrganizationAdapter;
import org.goafabric.core.ui.adapter.PractitionerAdapter;
import org.goafabric.core.ui.adapter.RoleAdapter;
import org.goafabric.core.ui.adapter.UserAdapter;
import org.goafabric.core.ui.extension.UserHolder;
import org.goafabric.core.ui.practice.tabs.OrganizationView;
import org.goafabric.core.ui.practice.tabs.PractitionerView;
import org.goafabric.core.ui.practice.tabs.RoleView;
import org.goafabric.core.ui.practice.tabs.UserView;

@Route(value = "practice", layout = MainView.class)
@PageTitle("Practice")
public class Organization extends VerticalLayout {

    public Organization(
            PractitionerAdapter practitionerAdapter, OrganizationAdapter organizationAdapter,
            UserAdapter userAdapter, RoleAdapter roleAdapter) {
        this.setSizeFull();

        TabSheet tabSheet = new TabSheet();
        tabSheet.setSizeFull();

        tabSheet.add("Practitioner", new PractitionerView(practitionerAdapter));
        tabSheet.add("Organization", new OrganizationView(organizationAdapter));

        if (UserHolder.userHasPermission(PermissionType.USERS.getValue())) {
            tabSheet.add("User", new UserView(userAdapter, roleAdapter));
            tabSheet.add("Role", new RoleView(roleAdapter));
        }
        add(tabSheet);
    }


}
