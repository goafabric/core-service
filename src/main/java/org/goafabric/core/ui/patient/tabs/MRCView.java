package org.goafabric.core.ui.patient.tabs;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.CallbackDataProvider;
import com.vaadin.flow.data.value.ValueChangeMode;
import org.goafabric.core.data.logic.PatientLogic;

import java.util.ArrayList;

public class MRCView extends VerticalLayout {
    private final PatientLogic patientLogic;
    private final VerticalLayout medicalRecordLayout = new VerticalLayout();

    private final ComboBox patientFilter = new ComboBox<>("", "Filter ...");
    private final TextField medicalRecordFilter = new TextField("", "Filter ...");

    private final MRCRecordComponent encounterComponent;

    public MRCView(PatientLogic patientLogic, MRCRecordComponent encounterComponent) {
        this.patientLogic = patientLogic;
        this.encounterComponent = encounterComponent;

        setSizeFull();
        addPatientsToFilter();
        this.add(patientFilter);
        this.add(medicalRecordFilter);

        addAddMedicalRecordButton();

        doEncounterStuff();
    }

    private void addPatientsToFilter() {
        patientFilter.setItems((CallbackDataProvider.FetchCallback<String, String>) query -> {
            medicalRecordFilter.setValue("");
            query.getLimit(); query.getOffset();
            var filter = query.getFilter().get();

            long start = System.currentTimeMillis();
            var lastNames = filter.equals("")
                    ? new ArrayList<String>().stream()
                    : patientLogic.findPatientNamesByFamilyName(filter).stream().map(
                    name -> name.getFamilyName() + ", " + name.getGivenName()).limit(query.getLimit());
            Notification.show("Search took " + (System.currentTimeMillis() -start) + " ms");
            return lastNames;
        });
    }

    private void addAddMedicalRecordButton() {
        var filterAddButton = new Button("+");
        this.add(filterAddButton);
        filterAddButton.addClickListener(event -> addMedicalRecordFilterEntries());
    }

    private void addMedicalRecordFilterEntries() {
        var typeCombo = new ComboBox<>("", "Anamnesis", "Diagnosis", "GOÄ");
        var filterCombo = new ComboBox<>("", "Filter ...");
        medicalRecordLayout.add(new HorizontalLayout(typeCombo, filterCombo));
        
        typeCombo.addValueChangeListener(event -> encounterComponent.loadMedicalCatalog(typeCombo.getValue(), filterCombo));
        typeCombo.setValue("Diagnosis");
    }

    private void doEncounterStuff() {
        this.add(medicalRecordLayout);
        patientFilter.addValueChangeListener( event -> showEncounter());
        patientFilter.setValue("Burns, Monty");

        medicalRecordFilter.setValueChangeMode(ValueChangeMode.LAZY);
        medicalRecordFilter.addValueChangeListener(event -> showEncounter());
    }

    private void showEncounter() {
        medicalRecordLayout.removeAll();
        var familyName = patientFilter.getValue().toString().split(",")[0];
        encounterComponent.processEncounters(patientLogic.findPatientNamesByFamilyName(familyName), medicalRecordFilter.getValue(), medicalRecordLayout);
    }


}
