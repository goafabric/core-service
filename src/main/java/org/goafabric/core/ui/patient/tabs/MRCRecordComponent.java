package org.goafabric.core.ui.patient.tabs;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import org.goafabric.core.data.repository.entity.PatientNamesOnly;
import org.goafabric.core.mrc.controller.vo.Encounter;
import org.goafabric.core.mrc.controller.vo.MedicalRecordType;
import org.goafabric.core.mrc.logic.EncounterLogic;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MRCRecordComponent {

    private final EncounterLogic encounterLogic;


    public MRCRecordComponent(EncounterLogic encounterLogic) {
        this.encounterLogic = encounterLogic;
    }

    public void processEncounters(List<PatientNamesOnly> patients, String encounterFilter, VerticalLayout encounterLayout) {
        if (!patients.isEmpty()) {
            long start = System.currentTimeMillis();

            var patientId = patients.get(0).getId();
            var encounters = encounterLogic.findByPatientIdAndText(patientId, encounterFilter);

            if (!encounters.isEmpty()) {
                encounters.forEach(encounter -> addMedicalRecords(encounter, encounterLayout));
            }
            Notification.show("Search took " + (System.currentTimeMillis() - start) + " ms");
        }
    }

    private void addMedicalRecords(Encounter encounter, VerticalLayout encounterLayout) {
        encounter.medicalRecords().forEach(medicalRecord -> {
            if (encounterLayout.getChildren().count() < 100) {
                var typeCombo = new ComboBox<>("", MedicalRecordType.values());
                var textField = new TextField("", medicalRecord.display());
                textField.setWidth("500px");
                typeCombo.setValue(medicalRecord.type());
                encounterLayout.add(new HorizontalLayout(typeCombo, textField));
            }
        });
    }


}
