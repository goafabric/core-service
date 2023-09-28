package org.goafabric.core.ui.mrc.tabs;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import org.goafabric.core.medicalrecords.controller.vo.MedicalRecord;
import org.goafabric.core.medicalrecords.controller.vo.MedicalRecordType;
import org.goafabric.core.ui.adapter.BodyMetricsAdapter;
import org.goafabric.core.ui.adapter.MedicalRecordAdapter;
import org.h2.util.StringUtils;
import org.springframework.context.ApplicationContext;

public class MedicalRecordDetailsView extends Dialog {
    private final ApplicationContext applicationContext;
    private final TextField displayTextField = new TextField();

    public MedicalRecordDetailsView(MedicalRecord medicalRecord, ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        this.setHeaderTitle(medicalRecord.type().getValue());

        var layout = new VerticalLayout();
        this.add(layout);

        if (medicalRecord.type().equals(MedicalRecordType.BODY_METRICS)) {
            addBodyMetric(medicalRecord, layout);
        }  else {
            addGenericType(medicalRecord, layout);
            addSaveButton(medicalRecord, layout);
        }

        addCancelButton(layout);
    }

    private void addCancelButton(VerticalLayout layout) {
        var buttonCancel = new Button("Cancel");
        buttonCancel.addClickListener(event -> this.close());
        layout.add(buttonCancel);
    }

    private void addSaveButton(MedicalRecord medicalRecord, VerticalLayout layout) {
        var buttonSave = new Button("Save");

        buttonSave.addClickListener(event -> {
                var changedRecord = new MedicalRecord(medicalRecord.id(), medicalRecord.version(), medicalRecord.type(), displayTextField.getValue(), medicalRecord.code(), medicalRecord.relation());
                applicationContext.getBean(MedicalRecordAdapter.class).save(changedRecord);
                this.close();
        });
        layout.add(buttonSave);
    }

    private void addBodyMetric(MedicalRecord medicalRecord, VerticalLayout layout) {
        var bodyMetrics = applicationContext.getBean(BodyMetricsAdapter.class).getById(medicalRecord.relation());
        layout.add(new TextField("Body Height", bodyMetrics.bodyHeight()));
        layout.add(new TextField("Belly Circumference ", bodyMetrics.bellyCircumference()));
        layout.add(new TextField("Head Circumference", bodyMetrics.headCircumference()));
        layout.add(new TextField("Body Fat", bodyMetrics.bodyFat()));
    }

    private void addGenericType(MedicalRecord medicalRecord, VerticalLayout layout) {
        displayTextField.setValue(medicalRecord.display());
        displayTextField.setWidth("500px");
        layout.add(displayTextField);

        if (!StringUtils.isNullOrEmpty(medicalRecord.code())) {
            layout.add(new TextField("Code", medicalRecord.code()));
        }
    }

}
