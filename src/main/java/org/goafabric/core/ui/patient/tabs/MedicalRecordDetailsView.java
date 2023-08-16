package org.goafabric.core.ui.patient.tabs;

import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import org.goafabric.core.mrc.repository.entity.MedicalRecordEo;

public class MedicalRecordDetailsView extends Dialog {
    public MedicalRecordDetailsView(MedicalRecordEo medicalRecord) {
        this.setHeaderTitle(medicalRecord.type);

        var layout = new VerticalLayout();
        var display = new TextField("Display", medicalRecord.display);
        display.setWidth("500px");

        layout.add(display);
        layout.add(new TextField("Code", medicalRecord.code));
        this.add(layout);
    }
}
