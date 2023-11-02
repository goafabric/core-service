package org.goafabric.core.ui.practice.tabs;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import org.goafabric.core.organization.controller.vo.Address;
import org.goafabric.core.organization.controller.vo.ContactPoint;
import org.goafabric.core.organization.controller.vo.Practitioner;
import org.goafabric.core.ui.GridView;
import org.goafabric.core.ui.adapter.SearchAdapter;

import java.util.Collections;

@PageTitle("Practitioner")
public class PractitionerView extends GridView<Practitioner> {

    public PractitionerView(SearchAdapter<Practitioner> logic) {
        super(new Grid<>(Practitioner.class), logic);
    }

    @Override
    protected void addColumns(Grid<Practitioner> grid) {
        grid.addColumn(p -> p.givenName()).setHeader("Given Name");
        grid.addColumn(p -> p.familyName()).setHeader("Family Name");
        grid.addColumn(p -> p.address().get(0).street()).setHeader("Street");
        grid.addColumn(p -> p.address().get(0).city()).setHeader("City");
    }

    protected void configureSaveDialog(Practitioner practitioner) {
        put(new TextField("Given Name", practitioner.givenName(), ""));
        put(new TextField("Family Name", practitioner.familyName(), ""));
        put(new TextField("Street", practitioner.address().get(0).street(), ""));
        put(new TextField("City", practitioner.address().get(0).city(), ""));
    }

    protected void onSave(Practitioner practitioner) {
        var address = practitioner.address().get(0);
        var updated = new Practitioner(
                isNewItem() ? null : practitioner.id(), isNewItem() ? null : practitioner.version(),
                mapDialogText.get("Given Name").getValue(),
                mapDialogText.get("Family Name").getValue(),
                practitioner.gender(), practitioner.birthDate(), practitioner.lanr(),
                Collections.singletonList(new Address(
                        isNewItem() ? null : address.id(), isNewItem() ? null : address.version(),
                        address.use(),
                        mapDialogText.get("Street").getValue(), mapDialogText.get("City").getValue(), address.postalCode(), address.state(), address.country())),
                isNewItem() ? Collections.singletonList(new ContactPoint(null, null, "", "", ""))
                        :  practitioner.contactPoint());
        getAdapter().save(updated);
    }

    protected void onDelete(Practitioner practitioner) {
        getAdapter().delete(practitioner.id());
    }

}
