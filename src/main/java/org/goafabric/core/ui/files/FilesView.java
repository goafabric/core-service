package org.goafabric.core.ui.files;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.goafabric.core.importexport.logic.ExportLogic;
import org.goafabric.core.importexport.logic.ImportLogic;
import org.goafabric.core.ui.MainView;
import org.goafabric.core.ui.adapter.ObjectStorageAdapter;
import org.goafabric.core.ui.files.tabs.ArchiveView;
import org.goafabric.core.ui.files.tabs.ImportExportView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

@Route(value = "files", layout = MainView.class)
@PageTitle("Files")
public class FilesView extends VerticalLayout {

    public FilesView(@Value("${spring.cloud.aws.s3.enabled:false}") Boolean s3Enabled,
                     @Autowired(required = false) ObjectStorageAdapter objectStorageAdapter, ImportLogic importLogic, ExportLogic exportLogic) {
        this.setSizeFull();

        TabSheet tabSheet = new TabSheet();
        tabSheet.setSizeFull();

        tabSheet.add("Archive", new ArchiveView(objectStorageAdapter));

        tabSheet.add("Import & Export", new ImportExportView(importLogic, exportLogic));

        add(tabSheet);
    }
}
