package org.goafabric.core.ui.practice;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.goafabric.core.data.logic.OrganizationLogic;
import org.goafabric.core.data.logic.PractitionerLogic;
import org.goafabric.core.ui.MainView;
import org.goafabric.core.ui.practice.tabs.OrganizationView;
import org.goafabric.core.ui.practice.tabs.PractitionerView;

@Route(value = "practice", layout = MainView.class)
@PageTitle("Practice")
public class PracticeView extends VerticalLayout {

    public PracticeView(
            PractitionerLogic practitionerLogic, OrganizationLogic organizationLogic) {
        this.setSizeFull();

        TabSheet tabSheet = new TabSheet();
        tabSheet.setSizeFull();

        tabSheet.add("Practitioner", new PractitionerView(practitionerLogic::findByGivenName));
        tabSheet.add("Organization", new OrganizationView(organizationLogic::findByName));
        //tabSheet.add("Roles", new RolesView(rolesLogic));


        add(tabSheet);
    }


}
