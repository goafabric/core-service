package org.goafabric.core.organization.controller;

import org.goafabric.core.organization.controller.dto.Permission;
import org.goafabric.core.organization.controller.dto.Role;
import org.goafabric.core.organization.controller.dto.types.PermissionCategory;
import org.goafabric.core.organization.controller.dto.types.PermissionType;
import org.goafabric.core.organization.logic.PermissionLogic;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
 class RoleControllerIT {
    @Autowired
    private RoleController controller;

    @Autowired
    private PermissionLogic permissionLogic;

    @Test
    void save() {
        var permissions = permissionLogic.saveAll(Arrays.asList(
                new Permission(null, null, PermissionCategory.VIEW, PermissionType.PATIENT),
                new Permission(null, null, PermissionCategory.VIEW, PermissionType.ORGANIZATION)
        ));

        var role = controller.save(
                new Role(null, null, "administrator", permissions));

        assertThat(controller.getById(role.id()).name())
                .isEqualTo("administrator");

        assertThat(controller.findByName("administrator").getFirst().name())
                .isEqualTo("administrator");

        assertThat(role.permissions()).hasSize(2);

        controller.deleteById(role.id());
    }

    @Test
    void findByName() {
        var permissions = permissionLogic.saveAll(Arrays.asList(
                new Permission(null, null, PermissionCategory.VIEW, PermissionType.PATIENT),
                new Permission(null, null, PermissionCategory.VIEW, PermissionType.ORGANIZATION)
                ));

        var role = controller.save(
                new Role(null, null, "administrator", permissions));
        
        assertThat(controller.findByName("administrator").getFirst().name())
                .isEqualTo("administrator");

        assertThat(role.permissions()).hasSize(2);

        controller.deleteById(role.id());
    }
}
