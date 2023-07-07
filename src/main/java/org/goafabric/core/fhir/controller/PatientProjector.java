package org.goafabric.core.fhir.controller;


import org.goafabric.core.data.logic.PatientLogic;
import org.goafabric.core.fhir.logic.mapper.FhirPatientMapper;
import org.goafabric.core.fhir.controller.vo.Bundle;
import org.goafabric.core.fhir.controller.vo.Patient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "fhir/Patient", produces = {MediaType.APPLICATION_JSON_VALUE, "application/fhir+json"})
public class PatientProjector {
    private final PatientLogic logic;
    private final FhirPatientMapper mapper;

    public PatientProjector(PatientLogic logic, FhirPatientMapper mapper) {
        this.logic = logic;
        this.mapper = mapper;
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, "application/fhir+json"})
    public void create(Patient patient) {
        logic.save(mapper.map(patient));
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
                .stream().map(o -> new Bundle.BundleEntryComponent<>(o, o.getClass().getSimpleName() + "/" + o.id)).toList());
    }

}
