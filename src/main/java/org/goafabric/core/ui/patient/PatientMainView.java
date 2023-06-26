package org.goafabric.core.ui.patient;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.goafabric.core.data.logic.PatientLogic;
import org.goafabric.core.ui.MainView;

import java.util.ArrayList;


@Route(value = "patient", layout = MainView.class)
@PageTitle("Patient")
public class PatientMainView extends VerticalLayout {

    public PatientMainView(
            PatientLogic patientLogic) {
        this.setSizeFull();

        TabSheet tabSheet = new TabSheet();
        tabSheet.setSizeFull();

        tabSheet.add("Patient", new PatientView(patientLogic::findByFamilyName));
        tabSheet.add("MRC", new MRCView(patientLogic,
                search -> new ArrayList<>(), search -> new ArrayList<>()));

        add(tabSheet);
    }
}
