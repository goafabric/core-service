package org.goafabric.core.ui.adapter;

import org.goafabric.core.medicalrecords.controller.ObjectStorageController;
import org.goafabric.core.medicalrecords.controller.vo.ObjectEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ObjectStorageAdapter implements SearchAdapter<ObjectEntry> {
    private final ObjectStorageController objectStorageController;


    public ObjectStorageAdapter(@Autowired(required = false) ObjectStorageController objectStorageController) {
        this.objectStorageController = objectStorageController;
    }

    @Override
    public List<ObjectEntry> search(String search) {
        return objectStorageController.search(search);
    }

    public void create(ObjectEntry objectEntry) {
        objectStorageController.save(objectEntry);
    }
}
