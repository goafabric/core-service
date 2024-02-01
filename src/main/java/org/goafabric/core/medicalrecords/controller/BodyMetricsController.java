package org.goafabric.core.medicalrecords.controller;

import jakarta.validation.Valid;
import org.goafabric.core.medicalrecords.controller.dto.BodyMetrics;
import org.goafabric.core.medicalrecords.controller.dto.MedicalRecord;
import org.goafabric.core.medicalrecords.logic.jpa.BodyMetricsLogic;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "/bodymetrics", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class BodyMetricsController {
    private final BodyMetricsLogic logic;

    public BodyMetricsController(BodyMetricsLogic logic) {
        this.logic = logic;
    }

    @GetMapping("getById/{id}")
    public BodyMetrics getById(@PathVariable("id") String id) {
        return logic.getById(id);
    }


    @PostMapping(value = "save", consumes = MediaType.APPLICATION_JSON_VALUE)
    public MedicalRecord save(@RequestBody @Valid BodyMetrics bodyMetrics) {
        return logic.save(bodyMetrics);
    }

    @GetMapping("delete/{id}")
    public void delete(@PathVariable("id") String id) {
        throw new IllegalStateException("Deletion not allowed here, should happen via Medical Record");
    }

}
