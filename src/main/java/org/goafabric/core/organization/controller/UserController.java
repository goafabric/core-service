package org.goafabric.core.organization.controller;

import jakarta.validation.Valid;
import org.goafabric.core.organization.controller.dto.User;
import org.goafabric.core.organization.controller.dto.UserInfo;
import org.goafabric.core.organization.controller.dto.types.PermissionCategory;
import org.goafabric.core.organization.controller.dto.types.PermissionType;
import org.goafabric.core.organization.logic.UserLogic;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class UserController {
    private final UserLogic logic;

    @GetMapping("getUserInfo")
    public UserInfo getUserInfo() {
        return logic.getUserInfo();
    }

}
