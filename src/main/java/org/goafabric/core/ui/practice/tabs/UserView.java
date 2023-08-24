package org.goafabric.core.ui.practice.tabs;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import org.goafabric.core.organization.controller.vo.Role;
import org.goafabric.core.organization.controller.vo.User;
import org.goafabric.core.ui.GridView;
import org.goafabric.core.ui.adapter.SearchAdapter;

import java.util.stream.Collectors;

@PageTitle("Practitioner")
public class UserView extends GridView<User> {

    public UserView(SearchAdapter<User> logic) {
        super(new Grid<>(User.class), logic);
    }

    @Override
    protected void addColumns(Grid<User> grid) {
        grid.addColumn(User::name).setHeader("Name");
        grid.addColumn(user -> user.roles().stream().map(Role::name)
                .collect(Collectors.joining(", "))).setHeader("Roles");
    }

    protected void configureSaveDialog(User user) {
        put(new TextField("Name", user.name(), ""));
    }

    protected void onSave(User user) {
        var updated = new User(
                user.id(), user.version(),
                user.patientId(),
                mapDialog.get("Name").getValue(),
                user.roles());
        getAdapter().save(updated);
    }


    protected void onDelete(User user) {
        getAdapter().delete(user.id());
    }

}
