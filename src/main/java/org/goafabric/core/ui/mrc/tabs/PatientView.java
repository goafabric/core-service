package org.goafabric.core.ui.mrc.tabs;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import org.goafabric.core.data.controller.vo.Patient;
import org.goafabric.core.ui.GridView;
import org.goafabric.core.ui.adapter.PatientAdapter;

@PageTitle("Patient")
public class PatientView extends GridView<Patient> {

    public PatientView(PatientAdapter adapter) {
        super(new Grid<>(Patient.class), adapter);
    }

    @Override
    protected void addColumns(Grid<Patient> grid) {
        grid.addColumn(p -> p.givenName()).setHeader("Given Name");
        grid.addColumn(p -> p.familyName()).setHeader("Family Name");
        grid.addColumn(p -> p.address().get(0).city()).setHeader("City");
        grid.addColumn(p -> p.address().get(0).street()).setHeader("Street");
    }

    protected void configureSaveDialog(Patient patient) {
        put(new TextField("Given Name", patient.givenName(), ""));
        put(new TextField("Family Name", patient.familyName(), ""));
        put(new TextField("City", patient.address().get(0).city(), ""));
        put(new TextField("Street", patient.address().get(0).street(), ""));
    }

    protected void onSave(Patient patient) {
        var patient2 = new Patient(patient.id(), patient.version(),
                mapDialog.get("Given Name").getValue(),
                mapDialog.get("Family Name").getValue(),
                patient.gender(), patient.birthDate(), patient.address(), patient.contactPoint());
        ((PatientAdapter)getAdapter()).save(patient2);
    }

}
