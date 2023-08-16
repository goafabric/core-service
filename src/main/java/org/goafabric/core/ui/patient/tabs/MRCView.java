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
import org.goafabric.core.mrc.logic.EncounterLogic;
import org.goafabric.core.ui.SearchLogic;
import org.goafabric.core.ui.adapter.vo.ChargeItem;
import org.goafabric.core.ui.adapter.vo.Condition;

import java.util.ArrayList;

public class MRCView extends VerticalLayout {
    private final PatientLogic patientLogic;
    private final SearchLogic<Condition> conditionLogic;
    private final SearchLogic<ChargeItem> chargeItemLogic;

    private final EncounterLogic encounterLogic;
    private final VerticalLayout encounterLayout = new VerticalLayout();

    private final ComboBox patientFilter = new ComboBox<>("", "Filter ...");
    private final TextField encounterFilter = new TextField("", "Filter ...");

    private final MRCEncounterComponent encounterComponent;

    public MRCView(PatientLogic patientLogic, EncounterLogic encounterLogic, SearchLogic<Condition> diagnosisLogic, SearchLogic<ChargeItem> chargeItemLogic, MRCEncounterComponent encounterComponent) {
        this.patientLogic = patientLogic;
        this.encounterLogic = encounterLogic;
        this.conditionLogic = diagnosisLogic;
        this.chargeItemLogic = chargeItemLogic;
        this.encounterComponent = encounterComponent;

        setSizeFull();
        addPatientsToFilter();
        this.add(patientFilter);
        this.add(encounterFilter);

        addAddEncounterButton();

        doEncounterStuff();
    }

    private void addPatientsToFilter() {
        patientFilter.setItems((CallbackDataProvider.FetchCallback<String, String>) query -> {
            encounterFilter.setValue("");
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

    private void addAddEncounterButton() {
        var filterAddButton = new Button("+");
        this.add(filterAddButton);
        filterAddButton.addClickListener(event -> addEncounterFilterEntries());
    }

    private void addEncounterFilterEntries() {
        var typeCombo = new ComboBox<>("", "Anamnesis", "Diagnosis", "GOÄ");
        var filterCombo = new ComboBox<>("", "Filter ...");
        encounterLayout.add(new HorizontalLayout(typeCombo, filterCombo));
        
        typeCombo.addValueChangeListener(event -> loadMedicalCatalog(typeCombo.getValue(), filterCombo));
        typeCombo.setValue("Diagnosis");
    }

    private void doEncounterStuff() {
        this.add(encounterLayout);
        patientFilter.addValueChangeListener( event -> showEncounter());
        patientFilter.setValue("Burns, Monty");

        encounterFilter.setValueChangeMode(ValueChangeMode.LAZY);
        encounterFilter.addValueChangeListener(event -> showEncounter());
    }

    private void showEncounter() {
        encounterLayout.removeAll();
        var familyName = patientFilter.getValue().toString().split(",")[0];
        encounterComponent.processEncounters(patientLogic.findPatientNamesByFamilyName(familyName), encounterFilter.getValue(), encounterLayout);
    }

    public void loadMedicalCatalog(String catalogType, ComboBox<String> filterCombo) {
        filterCombo.setItems((CallbackDataProvider.FetchCallback<String, String>) query -> {
            query.getLimit(); query.getOffset();
            var filter = query.getFilter().get();
            if (catalogType.equals("Diagnosis")) {
                return conditionLogic.search(filter).stream().map(d -> d.display()).limit(query.getLimit());
            }
            if (catalogType.equals("GOÄ")) {
                return chargeItemLogic.search(filter).stream().map(d -> d.display()).limit(query.getLimit());
            }
            return new ArrayList<String>().stream();
        });
    }


}
