package org.goafabric.core.ui.practice.tabs;

import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import org.goafabric.core.organization.controller.vo.Permission;
import org.goafabric.core.organization.controller.vo.Role;
import org.goafabric.core.ui.GridView;
import org.goafabric.core.ui.adapter.RoleAdapter;
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

    private MultiSelectComboBox<Permission> createPermissionCombo(Role role) {
        var permCombo = new MultiSelectComboBox<Permission>("Permissions");
        permCombo.setItemLabelGenerator(permission -> permission.type().getValue());
        permCombo.setItems(((RoleAdapter)getAdapter()).findAllPermissions());
        
        permCombo.select(role.permissions());
        return permCombo;
    }

    protected void onSave(Role role) {
        var permCombo = (MultiSelectComboBox<Permission>) mapDialogCombo.get("Permissions");
        var updated = new Role(
                isNewItem() ? null : role.id(), isNewItem() ? null : role.version(),
                mapDialog.get("Name").getValue(),
                permCombo.getSelectedItems().stream().toList());

        getAdapter().save(updated);
    }

    protected void onDelete(Role role) {
        getAdapter().delete(role.id());
    }

}
