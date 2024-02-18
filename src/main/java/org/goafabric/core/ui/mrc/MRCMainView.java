package org.goafabric.core.ui.mrc;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.goafabric.core.medicalrecords.logic.chatbot.BruteChatBot;
import org.goafabric.core.ui.MainView;
import org.goafabric.core.ui.adapter.LockAdapter;
import org.goafabric.core.ui.adapter.PatientAdapter;
import org.goafabric.core.ui.mrc.tabs.MedicalRecordComponent;
import org.goafabric.core.ui.mrc.tabs.MedicalRecordView;
import org.goafabric.core.ui.mrc.tabs.PatientView;


@Route(value = "patient", layout = MainView.class)
@PageTitle("Patient")
public class MRCMainView extends VerticalLayout {

    public MRCMainView(
            PatientAdapter patientAdapter, MedicalRecordComponent medicalRecordComponent, BruteChatBot chatBot, LockAdapter lockAdapter) {
        this.setSizeFull();

        TabSheet tabSheet = new TabSheet();
        tabSheet.setSizeFull();

        tabSheet.add("MRC", new MedicalRecordView(patientAdapter, medicalRecordComponent, chatBot, lockAdapter));
        tabSheet.add("Patient", new PatientView(patientAdapter));

        add(tabSheet);

        tabSheet.addAttachListener(event -> activation(event.toString()));

        tabSheet.addSelectedChangeListener(event -> activation(event.toString()));

    }

    public void activation(String key) {
        System.err.println();

    }
}
