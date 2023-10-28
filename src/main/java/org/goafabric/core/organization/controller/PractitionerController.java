package org.goafabric.core.organization.controller;

import jakarta.validation.Valid;
import org.goafabric.core.organization.controller.dto.Practitioner;
import org.goafabric.core.organization.logic.PractitionerLogic;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "/practitioners", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class PractitionerController {
    private final PractitionerLogic logic;

    public PractitionerController(PractitionerLogic logic) {
        this.logic = logic;
    }

    @DeleteMapping("deleteById/{id}")
    public void deleteById(@PathVariable("id") String id) {
        logic.deleteById(id);
    }

    @GetMapping("getById/{id}")
    public Practitioner getById(@PathVariable("id") String id) {
        return logic.getById(id);
    }

    @GetMapping("findByGivenName")
    public List<Practitioner> findByGivenName(@RequestParam("givenName") String givenName) {
        return logic.findByGivenName(givenName);
    }

    @GetMapping("findByFamilyName")
    public List<Practitioner> findByFamilyName(@RequestParam("familyName") String familyName) {
        return logic.findByFamilyName(familyName);
    }

    @PostMapping(value = "save", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Practitioner save(@RequestBody @Valid Practitioner practitioner) {
        return logic.save(practitioner);
    }

}
