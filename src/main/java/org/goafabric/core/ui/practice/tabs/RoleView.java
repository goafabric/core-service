package org.goafabric.core.ui.practice.tabs;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.router.PageTitle;
import org.goafabric.core.organization.controller.vo.Role;
import org.goafabric.core.ui.GridView;
import org.goafabric.core.ui.adapter.SearchAdapter;

@PageTitle("Practitioner")
public class RoleView extends GridView<Role> {

    public RoleView(SearchAdapter<Role> logic) {
        super(new Grid<>(Role.class), logic);
    }

    @Override
    protected void addColumns(Grid<Role> grid) {
        grid.addColumn(Role::name).setHeader("Name");
    }

}
