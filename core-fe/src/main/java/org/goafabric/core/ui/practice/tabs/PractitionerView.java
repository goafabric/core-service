package org.goafabric.core.ui.practice.tabs;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.router.PageTitle;
import org.goafabric.core.ui.GridView;
import org.goafabric.core.ui.SearchLogic;
import org.goafabric.core.ui.adapter.vo.Practitioner;

@PageTitle("Practitioner")
public class PractitionerView extends GridView<Practitioner> {

    public PractitionerView(SearchLogic<Practitioner> logic) {
        super(new Grid<>(Practitioner.class), logic);
    }

    @Override
    protected void addColumns(Grid<Practitioner> grid) {
        grid.addColumn(p -> p.givenName()).setHeader("First Name");
        grid.addColumn(p -> p.familyName()).setHeader("Last Name");
        grid.addColumn(p -> p.address().get(0).city()).setHeader("City");
        grid.addColumn(p -> p.address().get(0).street()).setHeader("Street");
    }
}
