package org.goafabric.core.fhir.projector;


import org.goafabric.core.data.logic.PatientLogic;
import org.goafabric.core.fhir.projector.mapper.FPatientMapper;
import org.goafabric.core.fhir.projector.vo.Bundle;
import org.goafabric.core.fhir.projector.vo.Patient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "fhir/Patient", produces = {MediaType.APPLICATION_JSON_VALUE, "application/fhir+json"})
public class PatientProjector {
    private final PatientLogic logic;
    private final FPatientMapper mapper;

    public PatientProjector(PatientLogic logic, FPatientMapper mapper) {
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
        var bundle = new Bundle<Patient>();
        mapper.map(logic.findByFamilyName(familyName))
                .forEach(o -> bundle.addEntry(new Bundle.BundleEntryComponent(o, o.getClass().getSimpleName() + "/" + o.id)));
        return bundle;
    }


}
