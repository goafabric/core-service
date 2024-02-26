package org.goafabric.core.medicalrecords.controller;

import org.goafabric.core.medicalrecords.controller.dto.ObjectEntry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequestMapping(value = "/objects", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class ObjectStorageController {
    private final ObjectStorageLogicNative logic;

    private final List<ObjectEntry> objectEntriesInMem = new ArrayList<>();

    private final Boolean s3Enabled;
    
    public ObjectStorageController(ObjectStorageLogicNative logic, @Value("${spring.cloud.aws.s3.enabled}") Boolean s3Enabled) {
        this.logic = logic;
        this.s3Enabled = s3Enabled;
    }

    @GetMapping("getById/{name}")
    public ObjectEntry getByName(@PathVariable("name") String name) {
        return s3Enabled
                ? logic.getById(name)
                : objectEntriesInMem.stream().filter(o -> o.objectName().equals(name)).findFirst().get();
    }

    @GetMapping("search")
    public List<ObjectEntry> search(@RequestParam("search") String search) {
        return s3Enabled
                ? logic.search(search)
                : objectEntriesInMem.stream().filter(o -> o.objectName().startsWith(search)).toList();
    }

    @PostMapping(value = "save", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void save(@RequestBody ObjectEntry objectEntry) {
        if (s3Enabled) {
            logic.save(objectEntry);
        } else {
            objectEntriesInMem.add(objectEntry);
        }
    }
}
