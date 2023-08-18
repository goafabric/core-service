package org.goafabric.core.ui.catalogs.tabs;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.router.PageTitle;
import org.goafabric.core.ui.GridView;
import org.goafabric.core.ui.SearchAdapter;
import org.goafabric.core.ui.adapter.vo.Condition;

@PageTitle("Condition")
public class ConditionView extends GridView<Condition> {

    public ConditionView(SearchAdapter<Condition> logic) {
        super(new Grid<>(Condition.class), logic);
    }

    @Override
    protected void addColumns(Grid<Condition> grid) {
        grid.addColumn(Condition::code).setHeader("code");
        grid.addColumn(Condition::display).setHeader("description");
        grid.addColumn(Condition::shortname).setHeader("short");
    }
}
