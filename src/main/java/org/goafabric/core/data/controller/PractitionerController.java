package org.goafabric.core.data.controller;

import jakarta.validation.Valid;
import org.goafabric.core.data.controller.vo.Practitioner;
import org.goafabric.core.data.logic.PractitionerLogic;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "/practitioners", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class PractitionerController {
    private final PractitionerLogic practitionerLogic;

    public PractitionerController(PractitionerLogic practitionerLogic) {
        this.practitionerLogic = practitionerLogic;
    }

    @GetMapping("getById/{id}")
    public Practitioner getById(@PathVariable("id") String id) {
        return practitionerLogic.getById(id);
    }

    @GetMapping("findAll")
    public List<Practitioner> findAll() {
        return practitionerLogic.findAll();
    }

    @GetMapping("findByGivenName")
    public List<Practitioner> findByGivenName(@RequestParam("givenName") String givenName) {
        return practitionerLogic.findByGivenName(givenName);
    }

    @GetMapping("findByFamilyName")
    public List<Practitioner> findByFamilyName(@RequestParam("familyName") String familyName) {
        return practitionerLogic.findByFamilyName(familyName);
    }

    @PostMapping(value = "save", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Practitioner save(@RequestBody @Valid Practitioner practitioner) {
        return practitionerLogic.save(practitioner);
    }

}
