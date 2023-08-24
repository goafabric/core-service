package org.goafabric.core.ui.adapter;

import org.goafabric.core.organization.controller.vo.Role;
import org.goafabric.core.organization.logic.RoleLogic;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RoleAdapter implements SearchAdapter<Role> {
    private final RoleLogic logic;

    public RoleAdapter(RoleLogic logic) {
        this.logic = logic;
    }

    @Override
    public List<Role> search(String search) {
        return logic.findByName(search);
    }

    public void save(Role role) {
        logic.save(role);
    }

    public void delete(String id) {
        logic.deleteById(id);
    }
}
