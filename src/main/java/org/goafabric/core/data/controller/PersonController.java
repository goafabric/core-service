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

    @GetMapping("findByFirstName")
    public List<Patient> findByFirstName(@RequestParam("firstName") String firstName) {
        return patientLogic.findByFirstName(firstName);
    }

    @GetMapping("findByLastName")
    public List<Patient> findByLastName(@RequestParam("lastName") String lastName) {
        return patientLogic.findByLastName(lastName);
    }

    @PostMapping(value = "save", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Patient save(@RequestBody @Valid Patient patient) {
        return patientLogic.save(patient);
    }

}
