package org.goafabric.core.medicalrecords.controller;

import org.goafabric.core.medicalrecords.controller.vo.ObjectEntry;
import org.goafabric.core.medicalrecords.logic.ObjectStorageLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequestMapping(value = "/objects", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class ObjectStorageController {
    private final ObjectStorageLogic logic;

    private final List<ObjectEntry> objectEntriesInMem = new ArrayList<>();
    
    public ObjectStorageController(@Autowired(required = false) ObjectStorageLogic logic) {
        this.logic = logic;
    }

    @GetMapping("getById/{name}")
    public ObjectEntry getByName(@PathVariable("name") String name) {
        return logic != null
                ? logic.getById(name)
                : objectEntriesInMem.stream().filter(o -> o.objectName().equals(name)).findFirst().get();
    }

    @GetMapping("search")
    public List<ObjectEntry> search(@RequestParam("search") String search) {
        return logic != null
                ? logic.search(search)
                : objectEntriesInMem.stream().filter(o -> o.objectName().startsWith(search)).toList();
    }

    @PostMapping(value = "save", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void save(@RequestBody ObjectEntry objectEntry) {
        if (logic != null) {
            logic.save(objectEntry);
        } else {
            objectEntriesInMem.add(objectEntry);
        }
    }
}
