package org.goafabric.core.ui.practice.tabs;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import org.goafabric.core.organization.controller.vo.Permission;
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

    protected void configureSaveDialog(Role role) {
        put(new TextField("Name", role.name(), ""));
        put(createPermissionCombo(role));
    }

    private ComboBox<Permission> createPermissionCombo(Role role) {
        var permCombo = new ComboBox<Permission>("Permissions");
        permCombo.setItemLabelGenerator(permission -> permission.type().getValue());
        permCombo.setItems(role.permissions());
        return permCombo;
    }

    protected void onSave(Role role) {
        var updated = new Role(
                role.id(), role.version(),
                mapDialog.get("Name").getValue(),
                role.permissions());
        getAdapter().save(updated);
    }

    protected void onDelete(Role role) {
        getAdapter().delete(role.id());
    }

}
