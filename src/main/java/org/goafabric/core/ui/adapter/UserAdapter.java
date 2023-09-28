package org.goafabric.core.ui.adapter;

import org.goafabric.core.organization.controller.UserController;
import org.goafabric.core.organization.controller.vo.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserAdapter implements SearchAdapter<User> {
    private final UserController logic;

    public UserAdapter(UserController practitionerLogic) {
        this.logic = practitionerLogic;
    }

    @Override
    public List<User> search(String search) {
        return logic.findByName(search);
    }

    public void save(User user) {
        logic.save(user);
    }

    public void delete(String id) {
        logic.deleteById(id);
    }
}
