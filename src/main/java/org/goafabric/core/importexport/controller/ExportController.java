package org.goafabric.core.importexport.controller;

import org.goafabric.core.importexport.logic.ExportLogic;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/exports", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class ExportController {
    private final ExportLogic logic;

    public ExportController(ExportLogic logic) {
        this.logic = logic;
    }

    @GetMapping("run")
    public void run (@RequestParam("path") String path) {
        logic.run(path);
    }

}
