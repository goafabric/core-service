package org.goafabric.core.ui.controller;

import org.goafabric.core.ui.adapter.InsuranceAdapter;
import org.goafabric.core.ui.adapter.dto.Insurance;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "insurances", produces = MediaType.APPLICATION_JSON_VALUE)
public class InsuranceController {
    private final InsuranceAdapter adapter;

    public InsuranceController(InsuranceAdapter repository) {
        this.adapter = repository;
    }


    @GetMapping("/findByDisplay")
    public List<Insurance> findByDisplay(@RequestParam("display") String display) {
        return adapter.search(display);
    }
}
