package org.goafabric.core.medicalrecords.controller;

import jakarta.validation.Valid;
import org.goafabric.core.medicalrecords.logic.MedicalRecordLogic;
import org.goafabric.core.medicalrecords.controller.vo.MedicalRecord;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "/medicalrecords", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class MedicalRecordController {
    private final MedicalRecordLogic logic;

    public MedicalRecordController(MedicalRecordLogic logic) {
        this.logic = logic;
    }

    @GetMapping("getById/{id}")
    public MedicalRecord getById(@PathVariable("id") String id) {
        return logic.getById(id);
    }


    @PostMapping(value = "save", consumes = MediaType.APPLICATION_JSON_VALUE)
    public MedicalRecord save(@RequestBody @Valid MedicalRecord medicalRecord) {
        return logic.save(medicalRecord);
    }

}
