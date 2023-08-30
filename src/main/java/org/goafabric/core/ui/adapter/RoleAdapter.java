package org.goafabric.core.ui.adapter;

import org.goafabric.core.organization.controller.vo.Permission;
import org.goafabric.core.organization.controller.vo.Role;
import org.goafabric.core.organization.logic.PermissionLogic;
import org.goafabric.core.organization.logic.RoleLogic;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RoleAdapter implements SearchAdapter<Role> {
    private final RoleLogic logic;
    private final PermissionLogic permissionLogic;

    public RoleAdapter(RoleLogic logic, PermissionLogic permissionLogic) {
        this.logic = logic;
        this.permissionLogic = permissionLogic;
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

    public List<Permission> findAllPermissions() {
        return permissionLogic.findAll();
    }
}
