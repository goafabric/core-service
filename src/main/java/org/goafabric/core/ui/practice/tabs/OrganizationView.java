package org.goafabric.core.ui.practice.tabs;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import org.goafabric.core.data.controller.vo.Address;
import org.goafabric.core.data.controller.vo.Organization;
import org.goafabric.core.ui.GridView;
import org.goafabric.core.ui.adapter.SearchAdapter;

import java.util.Collections;

@PageTitle("Organization")
public class OrganizationView extends GridView<Organization> {

    public OrganizationView(SearchAdapter<Organization> logic) {
        super(new Grid<>(Organization.class), logic);
    }

    @Override
    protected void addColumns(Grid<Organization> grid) {
        grid.addColumn(Organization::name).setHeader("Name");
        grid.addColumn(p -> p.address().get(0).street()).setHeader("Street");
        grid.addColumn(p -> p.address().get(0).city()).setHeader("City");
    }

    protected void configureSaveDialog(Organization organization) {
        put(new TextField("Name", organization.name(), ""));
        put(new TextField("Street", organization.address().get(0).street(), ""));
        put(new TextField("City", organization.address().get(0).city(), ""));
    }

    protected void onSave(Organization organization) {
        var address = organization.address().get(0);
        var updated = new Organization(organization.id(), organization.version(),
                mapDialog.get("Name").getValue(),
                Collections.singletonList(new Address(address.id(), address.version(), address.use(),
                        mapDialog.get("Street").getValue(), mapDialog.get("City").getValue(), address.postalCode(), address.state(), address.country())),
                organization.contactPoint());
        getAdapter().save(updated);
    }

    protected void onDelete(Organization organization) {
        getAdapter().delete(organization.id());
    }

}
