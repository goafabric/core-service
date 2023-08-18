package org.goafabric.core.ui.mrc.tabs;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.CallbackDataProvider;
import org.goafabric.core.data.repository.entity.PatientNamesOnly;
import org.goafabric.core.mrc.controller.vo.Encounter;
import org.goafabric.core.mrc.controller.vo.MedicalRecordType;
import org.goafabric.core.mrc.logic.EncounterLogic;
import org.goafabric.core.mrc.logic.MedicalRecordLogic;
import org.goafabric.core.ui.adapter.ChargeItemAdapter;
import org.goafabric.core.ui.adapter.ConditionAdapter;
import org.goafabric.core.ui.adapter.vo.ChargeItem;
import org.goafabric.core.ui.adapter.vo.Condition;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MedicalRecordComponent {

    private final EncounterLogic encounterLogic;
    private final ConditionAdapter conditionAdapter;
    private final ChargeItemAdapter chargeItemAdapter;

    private final MedicalRecordLogic medicalRecordLogic;

    private MedicalRecordDetailsView medicalRecordDetailsView = null;

    private final ApplicationContext applicationContext;

    public MedicalRecordComponent(EncounterLogic encounterLogic, ConditionAdapter conditionAdapter, ChargeItemAdapter chargeItemAdapter, MedicalRecordLogic medicalRecordRepository, ApplicationContext applicationContext) {
        this.encounterLogic = encounterLogic;
        this.conditionAdapter = conditionAdapter;
        this.chargeItemAdapter = chargeItemAdapter;
        this.medicalRecordLogic = medicalRecordRepository;
        this.applicationContext = applicationContext;
    }


    public void processEncounters(VerticalLayout encounterLayout, List<PatientNamesOnly> patients, String display, MedicalRecordType recordType) {
        if (!patients.isEmpty()) {
            long start = System.currentTimeMillis();

            var patientId = patients.get(0).getId();
            var encounters = encounterLogic.findByPatientIdAndDisplay(patientId, display);

            if (recordType != null) {
                encounters = filterRecordsInMemory(recordType, encounters);
            }

            if (!encounters.isEmpty()) {
                encounters.forEach(encounter -> {
                    //encounterLayout.add(new Hr());
                    encounterLayout.add(new HorizontalLayout(new DatePicker(encounter.encounterDate()), new TextField("", encounter.encounterName())));
                    encounterLayout.add(new Hr());
                    addMedicalRecords(encounterLayout, encounter);
                });
            }

            Notification.show("Search took " + (System.currentTimeMillis() - start) + " ms");
        }
    }

    private static List<Encounter> filterRecordsInMemory(MedicalRecordType recordType, List<Encounter> encounters) {
        return encounters.stream().map(e ->
                new Encounter(e.id(), e.patientId(), e.encounterDate(), e.encounterName(),
                 e.medicalRecords().stream().filter(record -> record.type().equals(recordType)).toList())).toList();
    }

    private void addMedicalRecords(VerticalLayout encounterLayout, Encounter encounter) {
        encounter.medicalRecords().forEach(medicalRecord -> {
            if (encounterLayout.getChildren().count() < 100) {
                var typeCombo = new ComboBox<>("", MedicalRecordType.values());
                var textField = new TextField("", medicalRecord.display());
                textField.setWidth("500px");

                textField.setId(medicalRecord.id());
                typeCombo.setValue(medicalRecord.type());
                encounterLayout.add(new HorizontalLayout(typeCombo, textField));

                textField.addFocusListener(listener -> showRecordDetails(listener.getSource().getId().get()));
            }
        });
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
