package org.goafabric.core.ui.catalogs.tabs;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.router.PageTitle;
import org.goafabric.core.ui.GridView;
import org.goafabric.core.ui.SearchLogic;
import org.goafabric.core.ui.adapter.vo.Insurance;

@PageTitle("Insurance")
public class InsuranceVIew extends GridView<Insurance> {

    public InsuranceVIew(SearchLogic<Insurance> logic) {
        super(new Grid<>(Insurance.class), logic);
    }

    @Override
    protected void addColumns(Grid<Insurance> grid) {
        grid.addColumn(Insurance::code).setHeader("ikk");
        grid.addColumn(Insurance::display).setHeader("description");
        grid.addColumn(Insurance::shortname).setHeader("short");
    }
}
