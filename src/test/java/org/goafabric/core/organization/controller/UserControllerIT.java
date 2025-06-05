package org.goafabric.core.organization.controller;

import org.goafabric.core.organization.controller.dto.Permission;
import org.goafabric.core.organization.controller.dto.Role;
import org.goafabric.core.organization.controller.dto.User;
import org.goafabric.core.organization.controller.dto.types.PermissionCategory;
import org.goafabric.core.organization.controller.dto.types.PermissionType;
import org.goafabric.core.organization.logic.PermissionLogic;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
 class UserControllerIT {
    @Autowired
    private RoleController roleController;

    @Autowired
    private UserController userController;

    @Autowired
    private PermissionLogic permissionLogic;

    @Test
    void save() {
        var permissions = permissionLogic.saveAll(Arrays.asList(
                new Permission(null, null, PermissionCategory.VIEW, PermissionType.PATIENT),
                new Permission(null, null, PermissionCategory.VIEW, PermissionType.ORGANIZATION)
        ));

        roleController.save(new Role(null, null, "administrator", permissions));
        roleController.save(new Role(null, null, "assistant", permissions));
        roleController.save(new Role(null, null, "user", permissions));

        var roles = roleController.findByName("");
        assertThat(roles).hasSize(3);
        assertThat(roles.getFirst().permissions()).hasSize(2);

        var user = userController.save(
                new User(null, null, "0", "user1", roles));

        assertThat(userController.getById(user.id()).name())
                .isEqualTo("user1");
        assertThat(userController.getById(user.id()).roles())
                .hasSize(3);

        assertThat(userController.hasPermission("user1", PermissionCategory.VIEW, PermissionType.PATIENT)).isTrue();
        assertThat(userController.hasPermission("user1", PermissionCategory.VIEW, PermissionType.INVOICE)).isFalse();
        userController.deleteById(user.id());
    }

    @Test
    void getUserInfo() {
        var userInfo = userController.getUserInfo();
        assertThat(userInfo.userName()).isEqualTo("anonymous");
        assertThat(userInfo.tenantId()).isEqualTo("0");
    }
}
