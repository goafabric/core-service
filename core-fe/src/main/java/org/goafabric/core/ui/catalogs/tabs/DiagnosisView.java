package org.goafabric.core.ui.catalogs.tabs;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.router.PageTitle;
import org.goafabric.core.ui.GridView;
import org.goafabric.core.ui.SearchLogic;
import org.goafabric.core.ui.adapter.vo.Diagnosis;

@PageTitle("Diagnosis")
public class DiagnosisView extends GridView<Diagnosis> {

    public DiagnosisView(SearchLogic<Diagnosis> logic) {
        super(new Grid<>(Diagnosis.class), logic);
    }

    @Override
    protected void addColumns(Grid<Diagnosis> grid) {
        grid.addColumn(Diagnosis::code).setHeader("code");
        grid.addColumn(Diagnosis::display).setHeader("description");
        grid.addColumn(Diagnosis::shortname).setHeader("short");
    }
}
