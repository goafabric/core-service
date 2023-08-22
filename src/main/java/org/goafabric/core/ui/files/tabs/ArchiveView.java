package org.goafabric.core.ui.files.tabs;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.upload.SucceededEvent;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import org.goafabric.core.organization.controller.vo.ObjectEntry;
import org.goafabric.core.ui.GridView;
import org.goafabric.core.ui.adapter.ObjectStorageAdapter;

import java.io.IOException;

public class ArchiveView extends GridView<ObjectEntry> {
    private final ObjectStorageAdapter objectStorageAdapter;

    public ArchiveView(ObjectStorageAdapter objectStorageAdapter) {
        super(new Grid<>(ObjectEntry.class), objectStorageAdapter);
        this.objectStorageAdapter = objectStorageAdapter;

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
            objectStorageAdapter.create(
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
