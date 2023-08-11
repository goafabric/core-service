package org.goafabric.core.ui.patient.tabs;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.CallbackDataProvider;
import com.vaadin.flow.data.value.ValueChangeMode;
import org.goafabric.core.data.logic.PatientLogic;
import org.goafabric.core.data.repository.entity.PatientFamilyNameOnly;
import org.goafabric.core.mrc.controller.vo.Encounter;
import org.goafabric.core.mrc.controller.vo.MedicalRecordType;
import org.goafabric.core.mrc.logic.EncounterLogic;
import org.goafabric.core.ui.SearchLogic;
import org.goafabric.core.ui.adapter.vo.ChargeItem;
import org.goafabric.core.ui.adapter.vo.Condition;

import java.util.ArrayList;

public class MRCView extends VerticalLayout {
    private final PatientLogic patientLogic;
    private final SearchLogic<Condition> diagnosisLogic;
    private final SearchLogic<ChargeItem> chargeItemLogic;

    private final EncounterLogic encounterLogic;
    private final VerticalLayout encounterLayout = new VerticalLayout();

    private final ComboBox patientFilter = new ComboBox<>("", "Filter ...");
    private final TextField encounterFilter = new TextField("", "Filter ...");

    public MRCView(PatientLogic patientLogic, EncounterLogic encounterLogic, SearchLogic<Condition> diagnosisLogic, SearchLogic<ChargeItem> chargeItemLogic) {
        this.patientLogic = patientLogic;
        this.encounterLogic = encounterLogic;
        this.diagnosisLogic = diagnosisLogic;
        this.chargeItemLogic = chargeItemLogic;

        setSizeFull();
        addPatientFilter();
        this.add(patientFilter);
        this.add(encounterFilter);

        addAddButton();

        doEncounterStuff();
    }

    private void addPatientFilter() {
        patientFilter.setItems((CallbackDataProvider.FetchCallback<String, String>) query -> {
            query.getLimit(); query.getOffset();
            var filter = query.getFilter().get();

            long start = System.currentTimeMillis();
            var lastNames = filter.equals("")
                    ? new ArrayList<String>().stream()
                    : patientLogic.searchFamilyNames(filter).stream().map(PatientFamilyNameOnly::getFamilyName).limit(query.getLimit());
            Notification.show("Search took " + (System.currentTimeMillis() -start) + " ms");
            return lastNames;
        });
    }

    private void addAddButton() {
        var filterAddButton = new Button("+");

        this.add(filterAddButton);
        filterAddButton.addClickListener(new ComponentEventListener<ClickEvent<Button>>() {
            @Override
            public void onComponentEvent(ClickEvent<Button> event) {
                addFilterEntry();
            }
        });
    }

    private void addFilterEntry() {
        var typeCombo = new ComboBox<>("", "Anamnesis", "Diagnosis", "GOÄ");
        var filterCombo = new ComboBox<>("", "Filter ...");
        encounterLayout.add(new HorizontalLayout(typeCombo, filterCombo));
        
        typeCombo.addValueChangeListener(event -> setItems(typeCombo, filterCombo));
        typeCombo.setValue("Diagnosis");
    }

    private void setItems(ComboBox<String> typeCombo, ComboBox<String> filterCombo) {
        filterCombo.setItems((CallbackDataProvider.FetchCallback<String, String>) query -> {
            query.getLimit(); query.getOffset();
            var filter = query.getFilter().get();
            if (typeCombo.getValue().equals("Diagnosis")) {
                return diagnosisLogic.search(filter).stream().map(d -> d.display()).limit(query.getLimit());
            }
            if (typeCombo.getValue().equals("GOÄ")) {
                return chargeItemLogic.search(filter).stream().map(d -> d.display()).limit(query.getLimit());
            }
            return new ArrayList<String>().stream();
        });
    }

    private void doEncounterStuff() {
        this.add(encounterLayout);
        patientFilter.addValueChangeListener( event -> {
            showEncounter();
            encounterFilter.setValue("");
        });

        patientFilter.setValue("Burns");
        encounterFilter.setValueChangeMode(ValueChangeMode.LAZY);
        encounterFilter.addValueChangeListener(event -> showEncounter());
    }

    private void showEncounter() {
        var encounterFilter = this.encounterFilter.getValue() != null ? this.encounterFilter.getValue().toString() : "";

        //if (encounterFilter.isEmpty() || encounterFilter.length() > 1) {
            encounterLayout.removeAll();
            var patients = patientLogic.searchFamilyNames(patientFilter.getValue() != null ? patientFilter.getValue().toString() : "");
            if (!patients.isEmpty()) {
                long start = System.currentTimeMillis();

                var patientId = patients.get(0).getId();
                var encounters = encounterLogic.findByPatientIdAndText(patientId, encounterFilter);

                if (!encounters.isEmpty()) {
                    encounters.forEach(this::processEncounter);
                }
                Notification.show("Search took " + (System.currentTimeMillis() - start) + " ms");
            }
        //}
    }

    private void processEncounter(Encounter encounter) {
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
