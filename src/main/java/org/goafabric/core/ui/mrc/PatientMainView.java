package org.goafabric.core.ui.mrc;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.goafabric.core.data.logic.PatientLogic;
import org.goafabric.core.mrc.logic.EncounterLogic;
import org.goafabric.core.ui.MainView;
import org.goafabric.core.ui.mrc.tabs.MedicalRecordComponent;
import org.goafabric.core.ui.mrc.tabs.MedicalRecordView;
import org.goafabric.core.ui.mrc.tabs.PatientView;


@Route(value = "patient", layout = MainView.class)
@PageTitle("Patient")
public class PatientMainView extends VerticalLayout {

    public PatientMainView(
            PatientLogic patientLogic, EncounterLogic encounterLogic, MedicalRecordComponent encounterComponent) {
        this.setSizeFull();

        TabSheet tabSheet = new TabSheet();
        tabSheet.setSizeFull();

        tabSheet.add("MRC", new MedicalRecordView(patientLogic, encounterComponent));
        tabSheet.add("Patient", new PatientView(patientLogic::findByFamilyName));

        add(tabSheet);
    }
}
