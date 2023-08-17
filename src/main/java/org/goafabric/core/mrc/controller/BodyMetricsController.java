package org.goafabric.core.mrc.controller;

import jakarta.validation.Valid;
import org.goafabric.core.mrc.controller.vo.BodyMetrics;
import org.goafabric.core.mrc.logic.BodyMetricsLogic;
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
    public void save(@RequestBody @Valid BodyMetrics bodyMetrics) {
        logic.save(bodyMetrics);
    }

}
