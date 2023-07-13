package org.goafabric.core.ui.files.tabs;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.progressbar.ProgressBar;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import org.goafabric.core.ui.adapter.nw.ExportLogic;
import org.goafabric.core.ui.adapter.nw.ImportLogic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@PageTitle("Import & Export")
public class ImportExportView extends VerticalLayout {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final TextField pathField = new TextField();
    private final ImportLogic importLogic;
    private final ExportLogic exportLogic;

    public ImportExportView(ImportLogic importLogic, ExportLogic exportLogic) {
        this.importLogic = importLogic;
        this.exportLogic = exportLogic;
        createView();
    }

    private void createView() {
        setSizeFull();

        pathField.setValue("/Users/andreas/Downloads");
        this.add(pathField);

        var importButton = new Button("import");
        var exportButton = new Button("export");
        this.add(new HorizontalLayout(pathField, importButton, exportButton));

        importButton.addClickListener(event -> importFiles(pathField.getValue()));
        exportButton.addClickListener(event -> exportFiles(pathField.getValue()));
    }

    private void importFiles(String path) {
        ProgressBar progressBar = createProgressBar();
        try {
            importLogic.run(path);
            var notification = Notification.show("Import successful");
            notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            var notification = Notification.show("Error during import : " + e.getMessage());
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
        } finally {
            //progressBar.setVisible(false);
            this.remove(progressBar);

        }
    }


    private void exportFiles(String path) {
        ProgressBar progressBar = createProgressBar();
        try {
            exportLogic.run(path);
            var notification = Notification.show("Export successful");
            notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            var notification = Notification.show("Error during export : " + e.getMessage());
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
        } finally {
            this.remove(progressBar);
        }
    }

    private ProgressBar createProgressBar() {
        var progressBar = new ProgressBar();
        progressBar.setIndeterminate(true);
        //Div progressBarLabel = new Div();
        //progressBarLabel.setText("Exporting data ...");
        add(progressBar);
        return progressBar;
    }

}
