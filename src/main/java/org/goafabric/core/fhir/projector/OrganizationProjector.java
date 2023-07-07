package org.goafabric.core.fhir.projector;

import org.goafabric.core.fhir.projector.mapper.FOrganizationMapper;
import org.goafabric.core.fhir.projector.vo.Bundle;
import org.goafabric.core.fhir.projector.vo.Organization;
import org.goafabric.core.data.logic.OrganizationLogic;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "fhir/Organization", produces = {MediaType.APPLICATION_JSON_VALUE, "application/fhir+json"})
public class OrganizationProjector implements FhirProjector<Organization> {
    private final OrganizationLogic logic;
    private final FOrganizationMapper mapper;

    public OrganizationProjector(OrganizationLogic logic, FOrganizationMapper mapper) {
        this.logic = logic;
        this.mapper = mapper;
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, "application/fhir+json"})
    public void create(Organization organization) {
        logic.save(mapper.map(organization));
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
        var bundle = new Bundle<Organization>();
        mapper.map(logic.findByName(name))
                .forEach(o -> bundle.addEntry(new Bundle.BundleEntryComponent(o, o.getClass().getSimpleName() + "/" + o.id)));
        return bundle;
    }
}
