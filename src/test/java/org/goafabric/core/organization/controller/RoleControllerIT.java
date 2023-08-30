package org.goafabric.core.organization.controller;

import org.goafabric.core.organization.controller.vo.Permission;
import org.goafabric.core.organization.controller.vo.Role;
import org.goafabric.core.organization.controller.vo.types.PermissionCategory;
import org.goafabric.core.organization.controller.vo.types.PermissionType;
import org.goafabric.core.organization.logic.PermissionLogic;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class RoleControllerIT {
    @Autowired
    private RoleController controller;

    @Autowired
    private PermissionLogic permissionLogic;

    @Test
    public void save() {
        var permissions = permissionLogic.saveAll(Arrays.asList(
                new Permission(null, null, PermissionCategory.VIEW, PermissionType.Patient),
                new Permission(null, null, PermissionCategory.VIEW, PermissionType.Practice)
        ));

        var role = controller.save(
                new Role(null, null, "administrator", permissions));

        assertThat(controller.getById(role.id()).name())
                .isEqualTo("administrator");

        assertThat(controller.findByName("administrator").get(0).name())
                .isEqualTo("administrator");

        assertThat(role.permissions()).hasSize(2);

        controller.deleteById(role.id());
    }

    @Test
    public void findByName() {
        var permissions = permissionLogic.saveAll(Arrays.asList(
                new Permission(null, null, PermissionCategory.VIEW, PermissionType.Patient),
                new Permission(null, null, PermissionCategory.VIEW, PermissionType.Practice)
                ));

        var role = controller.save(
                new Role(null, null, "administrator", permissions));
        
        assertThat(controller.findByName("administrator").get(0).name())
                .isEqualTo("administrator");

        assertThat(role.permissions()).hasSize(2);

        controller.deleteById(role.id());
    }
}
