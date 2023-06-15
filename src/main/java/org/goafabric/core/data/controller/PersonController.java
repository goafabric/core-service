package org.goafabric.core.data.controller;

import jakarta.validation.Valid;
import org.goafabric.core.data.controller.dto.Patient;
import org.goafabric.core.data.logic.PatientLogic;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "/persons", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class PersonController {
    private final PatientLogic patientLogic;

    public PersonController(PatientLogic patientLogic) {
        this.patientLogic = patientLogic;
    }

    @GetMapping("getById/{id}")
    public Patient getById(@PathVariable("id") String id) {
        return patientLogic.getById(id);
    }

    @GetMapping("findAll")
    public List<Patient> findAll() {
        return patientLogic.findAll();
    }

    @GetMapping("findByGivenName")
    public List<Patient> findByGivenName(@RequestParam("givenName") String givenName) {
        return patientLogic.findByGivenName(givenName);
    }

    @GetMapping("findByFamilyName")
    public List<Patient> findByFamilyName(@RequestParam("familyName") String familyName) {
        return patientLogic.findByFamilyName(familyName);
    }

    @PostMapping(value = "save", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Patient save(@RequestBody @Valid Patient patient) {
        return patientLogic.save(patient);
    }

}
