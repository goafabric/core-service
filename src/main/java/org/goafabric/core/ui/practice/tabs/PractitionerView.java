package org.goafabric.core.ui.practice.tabs;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.router.PageTitle;
import org.goafabric.core.data.controller.vo.Practitioner;
import org.goafabric.core.ui.GridView;
import org.goafabric.core.ui.adapter.SearchAdapter;

@PageTitle("Practitioner")
public class PractitionerView extends GridView<Practitioner> {

    public PractitionerView(SearchAdapter<Practitioner> logic) {
        super(new Grid<>(Practitioner.class), logic);
    }

    @Override
    protected void addColumns(Grid<Practitioner> grid) {
        grid.addColumn(p -> p.givenName()).setHeader("Given Name");
        grid.addColumn(p -> p.familyName()).setHeader("Family Name");
        grid.addColumn(p -> p.address().get(0).street()).setHeader("Street");
        grid.addColumn(p -> p.address().get(0).city()).setHeader("City");
    }
}
