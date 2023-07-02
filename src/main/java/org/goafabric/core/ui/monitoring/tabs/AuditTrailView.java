package org.goafabric.core.ui.monitoring.tabs;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.router.PageTitle;
import org.goafabric.core.ui.GridView;
import org.goafabric.core.ui.SearchLogic;
import org.goafabric.core.ui.adapter.vo.AuditEvent;

@PageTitle("Patient")
public class AuditTrailView extends GridView<AuditEvent> {

    public AuditTrailView(SearchLogic<AuditEvent> logic) {
        super(new Grid<>(AuditEvent.class), logic);
    }

    @Override
    protected void addColumns(Grid<AuditEvent> grid) {
        grid.addColumn(a -> a.operation).setHeader("operation");
        grid.addColumn(a -> a.createdBy).setHeader("created by");
        grid.addColumn(a -> a.modifiedBy).setHeader("modified by");
        grid.addColumn(a -> a.oldValue).setHeader("old value");
        grid.addColumn(a -> a.newValue).setHeader("new value");
    }
}
