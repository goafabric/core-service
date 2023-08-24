package org.goafabric.core.organization.controller;

import org.goafabric.core.organization.controller.vo.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class RoleControllerIT {
    @Autowired
    private RoleController controller;

    @Test
    public void save() {
        var role = controller.save(
                new Role(null, null, "administrator"));

        controller.save(
                new Role(null, null, "assistant"));

        controller.save(
                new Role(null, null, "user"));

        assertThat(controller.getById(role.id()).name())
                .isEqualTo("administrator");

        assertThat(controller.findByName("administrator").get(0).name())
                .isEqualTo("administrator");

        controller.deleteById(role.id());
    }

    public void findByName() {
        var role = controller.save(
                new Role(null, null, "administrator"));
        
        assertThat(controller.findByName("administrator").get(0).name())
                .isEqualTo("administrator");

        controller.deleteById(role.id());

    }
}
