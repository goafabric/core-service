package org.goafabric.core.ui.extension;

import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.VaadinServiceInitListener;
import org.goafabric.core.extensions.HttpInterceptor;
import org.goafabric.core.organization.controller.vo.User;
import org.goafabric.core.ui.adapter.UserAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UiInterceptor implements VaadinServiceInitListener {
    @Autowired
    private UserAdapter userAdapter;
    private static User user = null;

    @Override
    public void serviceInit(ServiceInitEvent event) {
        event.getSource().addUIInitListener(init -> {
            var userName = HttpInterceptor.getUserName();
            var users = userAdapter.search(userName);

            user = users.get(0);
        });
    }

    public static User getUser() {
        if (user != null) {
            return user;
        } else {
            throw new IllegalStateException("no user in context");
        }
        /*
        return user.get() != null ? user.get() : new User(null, null, null, "fallback",
                Collections.singletonList(new Role(null, null, "none")));

         */
    }
}
