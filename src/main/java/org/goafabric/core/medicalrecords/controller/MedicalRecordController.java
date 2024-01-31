package org.goafabric.core.medicalrecords.controller;

import jakarta.validation.Valid;
import org.goafabric.core.medicalrecords.controller.dto.MedicalRecord;
import org.goafabric.core.medicalrecords.logic.MedicalRecordLogicAble;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "/medicalrecords", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class MedicalRecordController {
    private final MedicalRecordLogicAble logic;

    public MedicalRecordController(MedicalRecordLogicAble logic) {
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

    @GetMapping("delete/{id}")
    public void delete(@PathVariable("id") String id) {
        logic.delete(id);
    }

}
