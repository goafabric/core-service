package org.goafabric.core.medicalrecords.controller;

import org.goafabric.core.medicalrecords.controller.vo.ObjectEntry;
import org.goafabric.core.medicalrecords.logic.ObjectStorageLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "/objects", produces = MediaType.APPLICATION_JSON_VALUE)
public class ObjectStorageController {
    private final ObjectStorageLogic logic;

    public ObjectStorageController(@Autowired(required = false) ObjectStorageLogic logic) {
        this.logic = logic;
    }

    @GetMapping("getById/{id}")
    public ObjectEntry getById(@PathVariable("id") String id) {
        return logic.getById(id);
    }

    @GetMapping("search")
    public List<ObjectEntry> search(@RequestParam("search") String search) {
        return logic.search(search);
    }

    @PostMapping(value = "save", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void save(@RequestBody ObjectEntry objectEntry) {
        logic.create(objectEntry);
    }
}
