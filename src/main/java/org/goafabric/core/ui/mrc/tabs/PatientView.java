package org.goafabric.core.ui.mrc.tabs;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import org.goafabric.core.organization.controller.vo.Address;
import org.goafabric.core.organization.controller.vo.ContactPoint;
import org.goafabric.core.organization.controller.vo.Patient;
import org.goafabric.core.ui.GridView;
import org.goafabric.core.ui.adapter.PatientAdapter;

import java.util.Collections;

@PageTitle("Patient")
public class PatientView extends GridView<Patient> {

    public PatientView(PatientAdapter adapter) {
        super(new Grid<>(Patient.class), adapter);
    }

    @Override
    protected void addColumns(Grid<Patient> grid) {
        grid.addColumn(p -> p.givenName()).setHeader("Given Name");
        grid.addColumn(p -> p.familyName()).setHeader("Family Name");
        grid.addColumn(p -> p.address().get(0).street()).setHeader("Street");
        grid.addColumn(p -> p.address().get(0).city()).setHeader("City");
    }

    protected void configureSaveDialog(Patient patient) {
        put(new TextField("Given Name", patient.givenName(), ""));
        put(new TextField("Family Name", patient.familyName(), ""));
        put(new TextField("Street", patient.address().get(0).street(), ""));
        put(new TextField("City", patient.address().get(0).city(), ""));
    }

    protected void onSave(Patient patient) {
        var address = patient.address().get(0);
        var updated = new Patient(
                isNewItem() ? null : patient.id(), isNewItem() ? null : patient.version(),
                mapDialog.get("Given Name").getValue(),
                mapDialog.get("Family Name").getValue(),
                patient.gender(), patient.birthDate(),
                Collections.singletonList(new Address(
                        isNewItem() ? null : address.id(), isNewItem() ? null : address.version(),
                        address.use(),
                        mapDialog.get("Street").getValue(), mapDialog.get("City").getValue(), address.postalCode(), address.state(), address.country())),
                isNewItem() ? Collections.singletonList(new ContactPoint(null, null, "", "", ""))
                        : patient.contactPoint());
        getAdapter().save(updated);
    }

    protected void onDelete(Patient patient) {
        getAdapter().delete(patient.id());
    }

}
