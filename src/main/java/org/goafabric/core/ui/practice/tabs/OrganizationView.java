package org.goafabric.core.ui.practice.tabs;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.router.PageTitle;
import org.goafabric.core.data.controller.vo.Organization;
import org.goafabric.core.ui.GridView;
import org.goafabric.core.ui.adapter.SearchAdapter;

@PageTitle("Organization")
public class OrganizationView extends GridView<Organization> {

    public OrganizationView(SearchAdapter<Organization> logic) {
        super(new Grid<>(Organization.class), logic);
    }

    @Override
    protected void addColumns(Grid<Organization> grid) {
        grid.addColumn(Organization::name).setHeader("name");
        grid.addColumn(p -> p.address().get(0).city()).setHeader("city");
        grid.addColumn(p -> p.address().get(0).street()).setHeader("street");
    }
}
