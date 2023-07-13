package org.goafabric.core.ui.files;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.goafabric.core.ui.MainView;
import org.goafabric.core.ui.adapter.nw.ExportLogic;
import org.goafabric.core.ui.adapter.nw.ImportLogic;
import org.goafabric.core.ui.adapter.nw.ObjectStorageLogic;
import org.goafabric.core.ui.files.tabs.ArchiveView;
import org.goafabric.core.ui.files.tabs.ImportExportView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

@Route(value = "files", layout = MainView.class)
@PageTitle("Files")
public class FilesView extends VerticalLayout {

    public FilesView(@Value("${spring.cloud.aws.s3.enabled:false}") Boolean s3Enabled,
                     @Autowired(required = false) ObjectStorageLogic objectStorageLogic, ImportLogic importLogic, ExportLogic exportLogic) {
        this.setSizeFull();

        TabSheet tabSheet = new TabSheet();
        tabSheet.setSizeFull();

        if (objectStorageLogic != null) {
            tabSheet.add("Archive", new ArchiveView(objectStorageLogic::search, objectStorageLogic));
        }
        tabSheet.add("Import & Export", new ImportExportView(importLogic, exportLogic));

        add(tabSheet);
    }
}
