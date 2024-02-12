package org.goafabric.core.ui.mrc.tabs;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.CallbackDataProvider;
import org.goafabric.core.organization.repository.entity.PatientNamesOnly;
import org.goafabric.core.medicalrecords.controller.dto.Encounter;
import org.goafabric.core.medicalrecords.controller.dto.MedicalRecord;
import org.goafabric.core.medicalrecords.controller.dto.MedicalRecordType;
import org.goafabric.core.ui.adapter.ChargeItemAdapter;
import org.goafabric.core.ui.adapter.ConditionAdapter;
import org.goafabric.core.ui.adapter.EncounterAdapter;
import org.goafabric.core.ui.adapter.MedicalRecordAdapter;
import org.goafabric.core.ui.adapter.dto.ChargeItem;
import org.goafabric.core.ui.adapter.dto.Condition;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MedicalRecordComponent {

    private final EncounterAdapter encounterAdapter;
    private final MedicalRecordAdapter medicalRecordLogic;

    private final ConditionAdapter conditionAdapter;
    private final ChargeItemAdapter chargeItemAdapter;


    private MedicalRecordDetailsView medicalRecordDetailsView = null;

    private final ApplicationContext applicationContext;

    public MedicalRecordComponent(EncounterAdapter encounterAdapter, MedicalRecordAdapter medicalRecordLogic, ConditionAdapter conditionAdapter, ChargeItemAdapter chargeItemAdapter, ApplicationContext applicationContext) {
        this.encounterAdapter = encounterAdapter;
        this.medicalRecordLogic = medicalRecordLogic;
        this.conditionAdapter = conditionAdapter;
        this.chargeItemAdapter = chargeItemAdapter;
        this.applicationContext = applicationContext;
    }

    public void processEncounters(VerticalLayout encounterLayout, List<PatientNamesOnly> patients, String display, List<MedicalRecordType> recordTypes) {
        if (!patients.isEmpty()) {
            long start = System.currentTimeMillis();

            var patientId = patients.get(0).getId();
            var encounters = encounterAdapter.findByPatientIdAndDisplayAndType(patientId, display, recordTypes);

            if (!encounters.isEmpty()) {
                encounters.forEach(encounter -> {
                    encounterLayout.add(new HorizontalLayout(new DatePicker(encounter.encounterDate()), new TextField("", encounter.encounterName())));
                    addMedicalRecords(encounterLayout, encounter);
                });
            }

            Notification.show("Search took " + (System.currentTimeMillis() - start) + " ms");
        }
    }

    private void addMedicalRecords(VerticalLayout encounterLayout, Encounter encounter) {
        var grid = new Grid<>(MedicalRecord.class);
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        encounterLayout.setSizeFull();
        grid.setSizeFull();
        grid.setColumns();

        grid.addColumn(r -> r.type()).setHeader("Type");
        grid.addColumn(r -> r.display()).setHeader("Text");
        grid.addColumn(r -> r.code()).setHeader("Code");

        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        encounterLayout.add(grid);
        grid.addItemDoubleClickListener(event -> showRecordDetails(event.getItem().id()));

        grid.setItems(encounter.medicalRecords().stream().limit(100).toList());

        /*
        encounter.medicalRecords().forEach(medicalRecord -> {
            if (encounterLayout.getChildren().count() < 100) {
                var typeCombo = new TextField("", medicalRecord.type().getValue(), "");
                var textField = new TextField("", medicalRecord.display(), "");
                textField.setWidth("500px");

                textField.setId(medicalRecord.id());
                encounterLayout.add(new HorizontalLayout(typeCombo, textField));

                textField.addFocusListener(listener -> showRecordDetails(listener.getSource().getId().get()));
            }
        });

         */
    }

    private void showRecordDetails(String id) {
        var record = medicalRecordLogic.getById(id);
        medicalRecordDetailsView = new MedicalRecordDetailsView(record, applicationContext);
        //medicalRecordDetailsView.setWidth("500px");
        medicalRecordDetailsView.open();
    }

    public void loadMedicalCatalog(String catalogType, ComboBox<String> filterCombo) {
        filterCombo.setItems((CallbackDataProvider.FetchCallback<String, String>) query -> {
            query.getLimit(); query.getOffset();
            var filter = query.getFilter().get();
            if (catalogType.equals("Diagnosis")) {
                return conditionAdapter.search(filter).stream().map(Condition::display).limit(query.getLimit());
            }
            if (catalogType.equals("GOÄ")) {
                return chargeItemAdapter.search(filter).stream().map(ChargeItem::display).limit(query.getLimit());
            }
            return new ArrayList<String>().stream();
        });
    }

}
