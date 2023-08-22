package org.goafabric.core.medicalrecords.controller;

import jakarta.validation.Valid;
import org.goafabric.core.medicalrecords.controller.vo.BodyMetrics;
import org.goafabric.core.medicalrecords.logic.BodyMetricsLogic;
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
    public BodyMetrics save(@RequestBody @Valid BodyMetrics bodyMetrics) {
        return logic.save(bodyMetrics);
    }

}
