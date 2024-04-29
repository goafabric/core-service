package org.goafabric.core.ui.extension;

import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.VaadinServiceInitListener;
import org.goafabric.core.extensions.TenantContext;
import org.goafabric.core.organization.controller.dto.User;
import org.goafabric.core.ui.adapter.UserAdapter;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class UserHolder implements ApplicationContextAware, VaadinServiceInitListener {

    private static ApplicationContext context;

    private static final Map<String, User> users = new ConcurrentHashMap<>();

    @Override
    public void serviceInit(ServiceInitEvent event) {
        event.getSource().addUIInitListener(init -> users.clear());
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    public static User getUser() {
        //Todo: we should do a restcall here without the userName, which will be provided via the HTTP Context, and that can return the user
        var httpUser = TenantContext.getUserName() != null ? TenantContext.getUserName() : "anonymousUser";
        var user = users.computeIfAbsent(httpUser, userName -> {
            var users = context.getBean(UserAdapter.class).search(userName);
            return users.size() == 1 ? users.get(0) : null;
        });

        if (user == null) {
            throw new IllegalStateException("no user in context");
        }
        return user;
    }

    public static boolean userHasRole(String roleName) {
        return getUser().roles().stream().anyMatch(role -> roleName.equals(role.name()));
    }

    public static boolean userIsAdmin() {
        return userHasRole("administrator");
    }

    public static boolean userHasPermission(String permissionName) {
        return true;
        /*
        return getUser().roles().stream().anyMatch(role ->
                role.permissions().stream().anyMatch(permission ->
                    permission.type().getValue().equals(permissionName)));

         */
    }


}
