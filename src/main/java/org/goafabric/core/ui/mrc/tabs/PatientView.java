package org.goafabric.core.ui.mrc.tabs;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.router.PageTitle;
import org.goafabric.core.data.controller.vo.Patient;
import org.goafabric.core.ui.GridView;
import org.goafabric.core.ui.SearchLogic;

@PageTitle("Patient")
public class PatientView extends GridView<Patient> {

    public PatientView(SearchLogic<Patient> logic) {
        super(new Grid<>(Patient.class), logic);
    }

    @Override
    protected void addColumns(Grid<Patient> grid) {
        grid.addColumn(p -> p.givenName()).setHeader("First Name");
        grid.addColumn(p -> p.familyName()).setHeader("Last Name");
        grid.addColumn(p -> p.address().get(0).city()).setHeader("City");
        grid.addColumn(p -> p.address().get(0).street()).setHeader("Street");
    }
}
