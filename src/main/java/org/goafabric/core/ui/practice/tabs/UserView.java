package org.goafabric.core.ui.practice.tabs;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import org.goafabric.core.organization.controller.dto.Role;
import org.goafabric.core.organization.controller.dto.User;
import org.goafabric.core.ui.GridView;
import org.goafabric.core.ui.adapter.RoleAdapter;
import org.goafabric.core.ui.adapter.SearchAdapter;

import java.util.Collections;
import java.util.stream.Collectors;

@PageTitle("Practitioner")
public class UserView extends GridView<User> {

    private final RoleAdapter roleAdapter;

    public UserView(SearchAdapter<User> logic, RoleAdapter roleAdapter) {
        super(new Grid<>(User.class), logic);
        this.roleAdapter = roleAdapter;
    }

    @Override
    protected void addColumns(Grid<User> grid) {
        grid.addColumn(User::name).setHeader("Name");
        grid.addColumn(user -> user.roles().stream().map(Role::name)
                .collect(Collectors.joining(", "))).setHeader("Roles");
    }

    protected void configureSaveDialog(User user) {
        put(new TextField("Name", user.name(), ""));

        put(createRoleCombo(user));
    }

    private ComboBox<Role> createRoleCombo(User user) {
        var roles = roleAdapter.search("");
        var roleCombo = new ComboBox<Role>("Role");
        roleCombo.setItemLabelGenerator(Role::name);
        roleCombo.setItems(roles);
        if (!user.roles().isEmpty()) {
            roleCombo.setValue(user.roles().get(0));
        }
        return roleCombo;
    }


    protected void onSave(User user) {
        var updated = new User(
                isNewItem() ? null : user.id(), isNewItem() ? null : user.version(),
                user.practitionerId(),
                mapDialogText.get("Name").getValue(),
                Collections.singletonList((Role) mapDialogCombo.get("Role").getValue())
        );
        getAdapter().save(updated);
    }


    protected void onDelete(User user) {
        getAdapter().delete(user.id());
    }

}
