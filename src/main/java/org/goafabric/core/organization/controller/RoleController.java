package org.goafabric.core.organization.controller;

import jakarta.validation.Valid;
import org.goafabric.core.organization.controller.dto.Role;
import org.goafabric.core.organization.logic.RoleLogic;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class RoleController {
    private final RoleLogic logic;

    public RoleController(RoleLogic logic) {
        this.logic = logic;
    }

    @DeleteMapping("deleteById/{id}")
    public void deleteById(@PathVariable("id") String id) {
        logic.deleteById(id);
    }

    @GetMapping("getById/{id}")
    public Role getById(@PathVariable("id") String id) {
        return logic.getById(id);
    }

    @GetMapping("findByName")
    public List<Role> findByName(@RequestParam("name") String name) {
        return logic.findByName(name);
    }


    @PostMapping(value = "save", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Role save(@RequestBody @Valid Role organization) {
        return logic.save(organization);
    }

}
