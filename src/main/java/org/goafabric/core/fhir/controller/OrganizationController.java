package org.goafabric.core.fhir.controller;

import org.goafabric.core.fhir.controller.vo.Bundle;
import org.goafabric.core.fhir.controller.vo.Organization;
import org.goafabric.core.fhir.logic.FhirLogic;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController(value = "FhirOrganizationController")
@RequestMapping(value = "fhir/Organization", produces = {MediaType.APPLICATION_JSON_VALUE, "application/fhir+json"})
public class OrganizationController implements FhirController<Organization> {
    private final FhirLogic<Organization> organizationLogic;

    public OrganizationController(FhirLogic<Organization> organizationLogic) {
        this.organizationLogic = organizationLogic;
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, "application/fhir+json"})
    public void create(Organization organization) {
        organizationLogic.create(organization);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        organizationLogic.delete(id);
    }

    @GetMapping("/{id}")
    public Organization getById(@PathVariable String id) {
        return organizationLogic.getById(id);
    }

    @GetMapping
    public Bundle<Organization> search(@RequestParam(value = "name", required = false) String name) {
        var bundle = new Bundle<Organization>();
        //organizationLogic.search(name).forEach(p -> bundle.addEntry(MockUtil.createBundleEntry(p, p.getId())));
        return bundle;
    }

}
