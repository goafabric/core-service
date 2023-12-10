package org.goafabric.core.organization.controller;

import jakarta.validation.Valid;
import org.goafabric.core.organization.controller.dto.Patient;
import org.goafabric.core.organization.logic.PatientLogic;
import org.goafabric.core.organization.repository.entity.PatientNamesOnly;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "/patients", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class PatientController {
    private final PatientLogic logic;

    public PatientController(PatientLogic logic) {
        this.logic = logic;
    }

    @DeleteMapping("deleteById/{id}")
    public void deleteById(@PathVariable("id") String id) {
        logic.deleteById(id);
    }

    @GetMapping("getById/{id}")
    public Patient getById(@PathVariable("id") String id) {
        return logic.getById(id);
    }

    @GetMapping("findByGivenName")
    public List<Patient> findByGivenName(@RequestParam("givenName") String givenName) {
        return logic.findByGivenName(givenName);
    }

    @GetMapping("findByFamilyName")
    public List<Patient> findByFamilyName(@RequestParam("familyName") String familyName) {
        return logic.findByFamilyName(familyName);
    }

    @GetMapping("findPatientNamesByFamilyName")
    public List<PatientNamesOnly> findPatientNamesByFamilyName(String search) {
        return logic.findPatientNamesByFamilyName(search);
    }

    @PostMapping(value = "save", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Patient save(@RequestBody @Valid Patient patient) {
        return logic.save(patient);
    }

}
