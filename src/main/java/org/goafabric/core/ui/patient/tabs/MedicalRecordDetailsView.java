package org.goafabric.core.ui.patient.tabs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import org.goafabric.core.mrc.controller.vo.BodyMetrics;
import org.goafabric.core.mrc.controller.vo.MedicalRecordType;
import org.goafabric.core.mrc.repository.entity.MedicalRecordEo;
import org.h2.util.StringUtils;

public class MedicalRecordDetailsView extends Dialog {
    public MedicalRecordDetailsView(MedicalRecordEo medicalRecord) {
        this.setHeaderTitle(medicalRecord.type);

        var layout = new VerticalLayout();
        this.add(layout);

        if (medicalRecord.type.equals(MedicalRecordType.BODY_METRICS.getValue())) {
            try {
               var bodyMetrics = new ObjectMapper().readValue(medicalRecord.relations, BodyMetrics.class);
               layout.add(new TextField("Body Height", bodyMetrics.bodyHeight()));
               layout.add(new TextField("Belly Circumference ", bodyMetrics.bellyCircumference()));
               layout.add(new TextField("Head Circumference", bodyMetrics.headCircumference()));
               layout.add(new TextField("Body Fat", bodyMetrics.bodyFat()));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }

        }  else {
            addGenericType(medicalRecord, layout);
        }

    }

    private static void addGenericType(MedicalRecordEo medicalRecord, VerticalLayout layout) {
        var display = new TextField("Display", medicalRecord.display);
        display.setWidth("500px");
        layout.add(display);

        if (!StringUtils.isNullOrEmpty(medicalRecord.code)) {
            layout.add(new TextField("Code", medicalRecord.code));
        }
    }

    private static <T> T readValue(String value, T clazz) {
        try {
            return (T) new ObjectMapper().readValue(value, clazz.getClass());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
