package org.goafabric.core.organization.controller;

import org.goafabric.core.organization.controller.vo.Role;
import org.goafabric.core.organization.controller.vo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class UserControllerIT {
    @Autowired
    private RoleController roleController;

    @Autowired
    private UserController userController;

    @Test
    public void save() {
        roleController.save(new Role(null, null, "administrator"));
        roleController.save(new Role(null, null, "assistant"));
        roleController.save(new Role(null, null, "user"));

        var roles = roleController.findByName("");
        assertThat(roles).hasSize(3);

        var user = userController.save(
                new User(null, null, "1", "user1", roles));

        assertThat(userController.getById(user.id()).name())
                .isEqualTo("user1");
        assertThat(userController.getById(user.id()).roles())
                .hasSize(3);

        userController.deleteById(user.id());

    }
}
