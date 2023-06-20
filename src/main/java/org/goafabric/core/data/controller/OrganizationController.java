package org.goafabric.core.data.controller;

import jakarta.validation.Valid;
import org.goafabric.core.data.controller.dto.Organization;
import org.goafabric.core.data.logic.OrganizationLogic;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "/organizations", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class OrganizationController {
    private final OrganizationLogic organizationLogic;

    public OrganizationController(OrganizationLogic organizationLogic) {
        this.organizationLogic = organizationLogic;
    }

    @GetMapping("getById/{id}")
    public Organization getById(@PathVariable("id") String id) {
        return organizationLogic.getById(id);
    }

    @GetMapping("findAll")
    public List<Organization> findAll() {
        return organizationLogic.findAll();
    }

    @GetMapping("findByGivenName")
    public List<Organization> findByName(@RequestParam("name") String name) {
        return organizationLogic.findByName(name);
    }


    @PostMapping(value = "save", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Organization save(@RequestBody @Valid Organization organization) {
        return organizationLogic.save(organization);
    }

}
