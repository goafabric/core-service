package org.goafabric.core.ui.mrc.tabs;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.CallbackDataProvider;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.BeforeLeaveEvent;
import com.vaadin.flow.router.BeforeLeaveObserver;
import org.goafabric.core.medicalrecords.controller.dto.MedicalRecordType;
import org.goafabric.core.medicalrecords.logic.chatbot.BruteChatBot;
import org.goafabric.core.organization.repository.entity.PatientNamesOnly;
import org.goafabric.core.ui.adapter.LockAdapter;
import org.goafabric.core.ui.adapter.PatientAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MedicalRecordView extends VerticalLayout implements BeforeEnterObserver, BeforeLeaveObserver {
    private final PatientAdapter patientAdapter;
    private final VerticalLayout medicalRecordLayout = new VerticalLayout();

    private final ComboBox patientFilter = new ComboBox<>("", "Filter ...");
    private final TextField medicalRecordFilter = new TextField("", "Filter ...");

    private final MedicalRecordComponent encounterComponent;

    private final BruteChatBot chatBot;

    private final LockAdapter lockAdapter;

    private MedicalRecordType medicalRecordType = null;

    public MedicalRecordView(PatientAdapter patientAdapter, MedicalRecordComponent encounterComponent, BruteChatBot chatBot, LockAdapter lockAdapter) {
        this.patientAdapter = patientAdapter;
        this.encounterComponent = encounterComponent;
        this.chatBot = chatBot;
        this.lockAdapter = lockAdapter;


        setSizeFull();
        addPatientsToFilter();

        medicalRecordFilter.setPrefixComponent(VaadinIcon.SEARCH.create());
        this.add(patientFilter);
        this.add(new HorizontalLayout(medicalRecordFilter, createMedicalRecordTypeButtons()));

        addChatField();

        //addAddMedicalRecordButton();

        doEncounterStuff();
    }

    public void beforeEnter(BeforeEnterEvent event) {
        System.err.println("acquire lock");
        var lock = lockAdapter.acquireLock("MedicalRecordView");
        if (lock.isLocked()) {
            Notification.show("View is Locked");
            this.setEnabled(false);
        }
    }

    public void beforeLeave(BeforeLeaveEvent event) {
        if (this.isEnabled()) { //only remove lock if we set it
            System.err.println("remove lock");
            lockAdapter.removeLock("MedicalRecordView");
        }
    }

    private void addChatField() {
        var chatField = new TextField();
        chatField.setWidthFull();
        chatField.setValue("\uD83E\uDD16 Hello ...");
        this.add(chatField);
        chatField.addValueChangeListener(value -> {
            var encounters = chatBot.chat(value.getValue(), getSelectedPatients().getFirst().getId());
            if (encounters.isEmpty()) {
                medicalRecordLayout.removeAll();
                Notification.show("Sorry Dave .. i can't do that for you");
            } else {
                Notification.show("I can do that ...");
                medicalRecordLayout.removeAll();
                encounterComponent.processEncounters(medicalRecordLayout, encounters);
            }
        });
    }

    private HorizontalLayout createMedicalRecordTypeButtons() {
        var allButton = new Button(new Icon(VaadinIcon.ARROW_BACKWARD ));
        var anamnesisButton = new Button(new Icon(VaadinIcon.OPEN_BOOK));
        var findingButton = new Button(new Icon(VaadinIcon.FILE_SEARCH));
        var conditionButton = new Button(new Icon(VaadinIcon.STETHOSCOPE));
        var chargItemButton = new Button(new Icon(VaadinIcon.MONEY));
        var therapyButton = new Button(new Icon(VaadinIcon.PILLS));

        allButton.addClickListener(event -> showEncounter(null));
        anamnesisButton.addClickListener(event -> showEncounter(MedicalRecordType.ANAMNESIS));
        findingButton.addClickListener(event -> showEncounter(MedicalRecordType.FINDING));
        conditionButton.addClickListener(event -> showEncounter(MedicalRecordType.CONDITION));
        chargItemButton.addClickListener(event -> showEncounter(MedicalRecordType.CHARGEITEM));
        therapyButton.addClickListener(event -> showEncounter(MedicalRecordType.THERAPY));

        return new HorizontalLayout(allButton, anamnesisButton, findingButton, conditionButton, chargItemButton, therapyButton);

    }

    private void addPatientsToFilter() {
        patientFilter.setItems((CallbackDataProvider.FetchCallback<String, String>) query -> {
            medicalRecordFilter.setValue("");
            query.getLimit(); query.getOffset();
            var filter = query.getFilter().get();

            long start = System.currentTimeMillis();
            var lastNames = patientAdapter.findPatientNamesByFamilyName(
                    getFamilyName(filter), getGivenName(filter)).stream().map(
                    name -> name.getFamilyName() + ", " + name.getGivenName()).limit(query.getLimit());
            Notification.show("Search took " + (System.currentTimeMillis() -start) + " ms");
            return lastNames;
        });
    }

    /*
    private void addAddMedicalRecordButton() {
        var filterAddButton = new Button("+");
        this.add(filterAddButton);
        filterAddButton.addClickListener(event -> addMedicalRecordFilterEntries());
    }

     */

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

    private void showEncounter(MedicalRecordType medicalRecordType) {
        this.medicalRecordType = medicalRecordType;
        showEncounter();
    }

    private void showEncounter() {
        medicalRecordLayout.removeAll();
        encounterComponent.processEncounters(medicalRecordLayout,
                getSelectedPatients(),
                    medicalRecordFilter.getValue(), medicalRecordType == null ? new ArrayList<>() : Collections.singletonList(medicalRecordType));
    }

    private List<PatientNamesOnly> getSelectedPatients() {
        return patientAdapter.findPatientNamesByFamilyName(getFamilyName(patientFilter.getValue()), getGivenName(patientFilter.getValue()));
    }


    static String getFamilyName(Object name) {
        return name != null ? name.toString().split(",")[0].trim() : "";
    }

    static String getGivenName(Object name) {
        if (name == null) { return ""; }
        String[] names = name.toString().split(",");
        return names.length == 2 ? names[1].trim() : "";
    }

}
