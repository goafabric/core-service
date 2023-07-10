package org.goafabric.core.data.controller;

import jakarta.validation.Valid;
import org.goafabric.core.data.controller.vo.Organization;
import org.goafabric.core.data.logic.OrganizationLogic;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "/organizations", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class OrganizationController {
    private final OrganizationLogic logic;

    public OrganizationController(OrganizationLogic logic) {
        this.logic = logic;
    }

    @DeleteMapping("deleteById/{id}")
    public void deleteById(@PathVariable("id") String id) {
        logic.deleteById(id);
    }

    @GetMapping("getById/{id}")
    public Organization getById(@PathVariable("id") String id) {
        return logic.getById(id);
    }

    @GetMapping("findByName")
    public List<Organization> findByName(@RequestParam("name") String name) {
        return logic.findByName(name);
    }


    @PostMapping(value = "save", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Organization save(@RequestBody @Valid Organization organization) {
        return logic.save(organization);
    }

}
