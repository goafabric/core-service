package org.goafabric.core.ui.mrc.tabs;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import org.goafabric.core.data.controller.vo.Patient;
import org.goafabric.core.ui.GridView;
import org.goafabric.core.ui.adapter.SearchAdapter;

import java.util.HashMap;

@PageTitle("Patient")
public class PatientView extends GridView<Patient> {

    public PatientView(SearchAdapter<Patient> logic) {
        super(new Grid<>(Patient.class), logic);
    }

    @Override
    protected void addColumns(Grid<Patient> grid) {
        grid.addColumn(p -> p.givenName()).setHeader("Given Name");
        grid.addColumn(p -> p.familyName()).setHeader("Given Name");
        grid.addColumn(p -> p.address().get(0).city()).setHeader("City");
        grid.addColumn(p -> p.address().get(0).street()).setHeader("Street");

        grid.addSelectionListener(event -> {
            var patient = event.getFirstSelectedItem().get();

            put(new TextField("Given Name", patient.familyName()));
            put(new TextField("Family Name", patient.familyName()));
            put(new TextField("City", patient.address().get(0).city()));
            put(new TextField("Street", patient.address().get(0).street()));

            var dialog = new Dialog();
            mapDialog.values().forEach(dialog::add);

            var saveButton = new Button("save");

            saveButton.addClickListener(click -> {
                var patient2 = new Patient(patient.id(),
                        mapDialog.get("Given Name").getValue(),
                        mapDialog.get("Family Name").getValue(),
                        patient.gender(), patient.birthDate(), patient.address(), patient.contactPoint());
            });

        });
    }

    private final HashMap<String, TextField> mapDialog = new HashMap();

    private void put(TextField textField) {
        mapDialog.put(textField.getLabel(), textField);
    }
}
