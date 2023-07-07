package org.goafabric.core.fhir.controller;


import org.goafabric.core.fhir.controller.vo.Bundle;
import org.goafabric.core.fhir.controller.vo.Patient;
import org.goafabric.core.fhir.logic.FhirLogic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController(value = "FhirPatientController")
@RequestMapping(value = "fhir/Patient", produces = {MediaType.APPLICATION_JSON_VALUE, "application/fhir+json"})
public class PatientController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final FhirLogic<Patient> patientLogic;

    public PatientController(FhirLogic<Patient> patientLogic) {
        this.patientLogic = patientLogic;
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, "application/fhir+json"})
    public void create(Patient patient) {
        patientLogic.create(patient);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        patientLogic.delete(id);
    }

    @GetMapping("/{id}")
    public Patient getById(@PathVariable String id) {
        return patientLogic.getById(id);
    }

    @GetMapping
    public Bundle<Patient> search(@RequestParam(value = "family", required = false) String familyName) {
        var bundle = new Bundle<Patient>();
        patientLogic.search(familyName).forEach(o -> bundle.addEntry(createBundleEntry(o, o.id)));
        return bundle;
    }

    public static Bundle.BundleEntryComponent createBundleEntry(Object resource, String id) {
        return new Bundle.BundleEntryComponent(resource, resource.getClass().getSimpleName() + "/" + id);
    }

}
