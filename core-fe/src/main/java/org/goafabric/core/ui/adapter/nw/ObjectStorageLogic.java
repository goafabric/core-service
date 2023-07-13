package org.goafabric.core.ui.adapter.nw;

import org.goafabric.core.ui.SearchLogic;
import org.goafabric.core.ui.adapter.vo.ObjectEntry;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ObjectStorageLogic implements SearchLogic<ObjectEntry> {
    public void create(ObjectEntry objectEntry) {
    }

    public List<ObjectEntry> search(String s) {
        return  new ArrayList<>();
    }
}
