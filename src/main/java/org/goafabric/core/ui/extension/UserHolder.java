package org.goafabric.core.ui.extension;

import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.VaadinServiceInitListener;
import org.goafabric.core.extensions.HttpInterceptor;
import org.goafabric.core.organization.controller.vo.User;
import org.goafabric.core.ui.adapter.UserAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserHolder implements VaadinServiceInitListener {
    @Autowired
    private UserAdapter userAdapter;
    private static User user = null;

    @Override
    public void serviceInit(ServiceInitEvent event) {
        event.getSource().addUIInitListener(init -> {
            var userName = HttpInterceptor.getUserName();
            var users = userAdapter.search(userName);

            if (users.size() == 1) {
                user = users.get(0);
            }
        });
    }

    public static User getUser() {
        if (user != null) {
            return user;
        } else {
            throw new IllegalStateException("no user in context");
        }
    }

    public static boolean userHasRole(String roleName) {
        return getUser().roles().stream().anyMatch(role -> roleName.equals(role.name()));
    }

    public static boolean userIsAdmin() {
        return userHasRole("administrator");
    }

    public static boolean userHasPermission(String permissionName) {
        return getUser().roles().stream().anyMatch(role ->
                role.permissions().stream().anyMatch(permission ->
                    permission.type().getValue().equals(permissionName)));
    }
}
