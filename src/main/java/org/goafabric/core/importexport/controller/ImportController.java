package org.goafabric.core.importexport.controller;

import org.goafabric.core.importexport.logic.ImportLogic;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/imports", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class ImportController {
    private final ImportLogic logic;

    public ImportController(ImportLogic logic) {
        this.logic = logic;
    }

    @GetMapping("run")
    public void run (@RequestParam("path") String path) {
        logic.run(path);
    }

}
