package org.goafabric.core.ui.patient.tabs;

import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import org.goafabric.core.mrc.controller.vo.MedicalRecord;
import org.goafabric.core.mrc.controller.vo.MedicalRecordType;
import org.goafabric.core.mrc.logic.BodyMetricsLogic;
import org.h2.util.StringUtils;
import org.springframework.context.ApplicationContext;

public class MedicalRecordDetailsView extends Dialog {
    private final ApplicationContext applicationContext;

    public MedicalRecordDetailsView(MedicalRecord medicalRecord, ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        this.setHeaderTitle(medicalRecord.type().getValue());

        var layout = new VerticalLayout();
        this.add(layout);

        if (medicalRecord.type().equals(MedicalRecordType.BODY_METRICS)) {
            addBodyMetric(medicalRecord, layout);
        }  else {
            addGenericType(medicalRecord, layout);
        }

    }

    private void addBodyMetric(MedicalRecord medicalRecord, VerticalLayout layout) {
        var bodyMetrics = applicationContext.getBean(BodyMetricsLogic.class).getById(medicalRecord.relation());
        layout.add(new TextField("Body Height", bodyMetrics.bodyHeight()));
        layout.add(new TextField("Belly Circumference ", bodyMetrics.bellyCircumference()));
        layout.add(new TextField("Head Circumference", bodyMetrics.headCircumference()));
        layout.add(new TextField("Body Fat", bodyMetrics.bodyFat()));
    }

    private static void addGenericType(MedicalRecord medicalRecord, VerticalLayout layout) {
        var display = new TextField("Display", medicalRecord.display());
        display.setWidth("500px");
        layout.add(display);

        if (!StringUtils.isNullOrEmpty(medicalRecord.code())) {
            layout.add(new TextField("Code", medicalRecord.code()));
        }
    }

}
