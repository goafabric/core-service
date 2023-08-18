package org.goafabric.core.ui.files.tabs;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.upload.SucceededEvent;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import org.goafabric.core.data.controller.vo.ObjectEntry;
import org.goafabric.core.data.logic.ObjectStorageLogic;
import org.goafabric.core.ui.GridView;
import org.goafabric.core.ui.SearchAdapter;

import java.io.IOException;

public class ArchiveView extends GridView<ObjectEntry> {
    private final ObjectStorageLogic objectStorageLogic;

    public ArchiveView(SearchAdapter<ObjectEntry> logic, ObjectStorageLogic objectStorageLogic) {
        super(new Grid<>(ObjectEntry.class), logic);
        this.objectStorageLogic = objectStorageLogic;

        addUpload();
    }

    private void addUpload() {
        var memoryBuffer = new MemoryBuffer();
        var singleFileUpload = new Upload(memoryBuffer);
        singleFileUpload.addSucceededListener(event -> uploadFile(memoryBuffer, event));
        this.add(singleFileUpload);
    }

    private void uploadFile(MemoryBuffer memoryBuffer, SucceededEvent event) {
        try {
            objectStorageLogic.create(
                    new ObjectEntry(event.getFileName(), event.getMIMEType(), event.getContentLength(),
                            memoryBuffer.getInputStream().readAllBytes())
            );
            updateList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void addColumns(Grid<ObjectEntry> grid) {
        grid.addColumn(ObjectEntry::objectName).setHeader("Name");
        grid.addColumn(ObjectEntry::contentType).setHeader("Type");
        grid.addColumn(ObjectEntry::objectSize).setHeader("Size");
    }
}
