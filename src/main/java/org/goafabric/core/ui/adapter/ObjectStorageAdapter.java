package org.goafabric.core.ui.adapter;

import org.goafabric.core.data.controller.vo.ObjectEntry;
import org.goafabric.core.data.logic.ObjectStorageLogic;
import org.goafabric.core.ui.SearchAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ObjectStorageAdapter implements SearchAdapter<ObjectEntry> {
    private final ObjectStorageLogic objectStorageLogic;

    private final List<ObjectEntry> objectEntriesInMem = new ArrayList<>();

    public ObjectStorageAdapter(@Autowired(required = false) ObjectStorageLogic objectStorageLogic) {
        this.objectStorageLogic = objectStorageLogic;
    }

    @Override
    public List<ObjectEntry> search(String search) {
        return objectStorageLogic != null
                ? objectStorageLogic.search(search)
                : objectEntriesInMem.stream().filter(o -> o.objectName().startsWith(search)).toList();
    }

    public void create(ObjectEntry objectEntry) {
        if (objectStorageLogic != null) {
            objectStorageLogic.create(objectEntry);
        } else {
            objectEntriesInMem.add(objectEntry);
        }
    }
}
