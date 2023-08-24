package org.goafabric.core.medicalrecords.controller;

import jakarta.validation.Valid;
import org.goafabric.core.medicalrecords.controller.vo.Encounter;
import org.goafabric.core.medicalrecords.logic.EncounterLogic;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "/encounters", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class EncounterController {
    private final EncounterLogic logic;

    public EncounterController(EncounterLogic logic) {
        this.logic = logic;
    }


    @GetMapping("findByPatientIdAndDisplay")
    public List<Encounter> findByPatientIdAndDisplay(String patientId, String display) {
        return logic.findByPatientIdAndDisplay(patientId, display);
    }

    @PostMapping(value = "save", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Encounter save(@RequestBody @Valid Encounter encounter) {
        return logic.save(encounter);
    }

}
