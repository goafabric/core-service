package org.goafabric.core.fhir.r4.controller;


import org.goafabric.core.organization.logic.PatientLogic;
import org.goafabric.core.fhir.r4.logic.mapper.FhirPatientMapper;
import org.goafabric.core.fhir.r4.controller.dto.Bundle;
import org.goafabric.core.fhir.r4.controller.dto.Patient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "fhir/r4/Patient", produces = {MediaType.APPLICATION_JSON_VALUE, "application/fhir+json"})
public class PatientFhirController {
    private final PatientLogic logic;
    private final FhirPatientMapper mapper;

    public PatientFhirController(PatientLogic logic, FhirPatientMapper mapper) {
        this.logic = logic;
        this.mapper = mapper;
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, "application/fhir+json"})
    public void create(Patient patient) {
        throw new IllegalStateException("NYI");
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        logic.deleteById(id);
    }

    @GetMapping("/{id}")
    public Patient getById(@PathVariable String id) {
        return mapper.map(logic.getById(id));
    }

    @GetMapping
    public Bundle<Patient> search(@RequestParam(value = "family", required = false) String familyName) {
        return new Bundle<>(mapper.map(logic.findByFamilyName(familyName))
                .stream().map(o -> new Bundle.BundleEntryComponent<>(o, o.getClass().getSimpleName() + "/" + o.id())).toList());
    }

}
