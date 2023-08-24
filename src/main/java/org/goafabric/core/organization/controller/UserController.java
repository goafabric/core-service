package org.goafabric.core.organization.controller;

import jakarta.validation.Valid;
import org.goafabric.core.organization.controller.vo.User;
import org.goafabric.core.organization.logic.UserLogic;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "/roles", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class UserController {
    private final UserLogic logic;

    public UserController(UserLogic logic) {
        this.logic = logic;
    }

    @DeleteMapping("deleteById/{id}")
    public void deleteById(@PathVariable("id") String id) {
        logic.deleteById(id);
    }

    @GetMapping("getById/{id}")
    public User getById(@PathVariable("id") String id) {
        return logic.getById(id);
    }

    @GetMapping("findByName")
    public List<User> findByName(@RequestParam("name") String name) {
        return logic.findByName(name);
    }


    @PostMapping(value = "save", consumes = MediaType.APPLICATION_JSON_VALUE)
    public User save(@RequestBody @Valid User organization) {
        return logic.save(organization);
    }

}
