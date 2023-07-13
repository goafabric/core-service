package org.goafabric.core.fhir.r4.controller;

import org.goafabric.core.data.logic.OrganizationLogic;
import org.goafabric.core.fhir.r4.logic.mapper.FhirOrganizationMapper;
import org.goafabric.core.fhir.r4.controller.vo.Bundle;
import org.goafabric.core.fhir.r4.controller.vo.Organization;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "fhir/r4/Organization", produces = {MediaType.APPLICATION_JSON_VALUE, "application/fhir+json"})
public class OrganizationFhirProjector implements FhirProjector<Organization> {
    private final OrganizationLogic logic;
    private final FhirOrganizationMapper mapper;

    public OrganizationFhirProjector(OrganizationLogic logic, FhirOrganizationMapper mapper) {
        this.logic = logic;
        this.mapper = mapper;
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, "application/fhir+json"})
    public void create(Organization organization) {
        throw new IllegalStateException("NYI");
        //logic.save(mapper.map(organization));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        logic.deleteById(id);
    }

    @GetMapping("/{id}")
    public Organization getById(@PathVariable String id) {
        return mapper.map(logic.getById(id));
    }

    @GetMapping
    public Bundle<Organization> search(@RequestParam(value = "name", required = false) String name) {
        return new Bundle<>(mapper.map(logic.findByName(name))
                        .stream().map(o -> new Bundle.BundleEntryComponent<>(o, o.getClass().getSimpleName() + "/" + o.id())).toList());
    }
}
