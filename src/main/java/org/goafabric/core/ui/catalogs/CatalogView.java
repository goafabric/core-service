package org.goafabric.core.ui.catalogs;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.goafabric.core.ui.MainView;
import org.goafabric.core.ui.adapter.ChargeItemAdapter;
import org.goafabric.core.ui.adapter.DiagnosisAdapter;
import org.goafabric.core.ui.adapter.InsuranceAdapter;
import org.goafabric.core.ui.catalogs.tabs.ChargeItemView;
import org.goafabric.core.ui.catalogs.tabs.DiagnosisView;
import org.goafabric.core.ui.catalogs.tabs.InsuranceVIew;

@Route(value = "catalogs", layout = MainView.class)
@PageTitle("Catalogs")
public class CatalogView extends VerticalLayout {

    public CatalogView(InsuranceAdapter insuranceAdapter, DiagnosisAdapter diagnosisAdapter, ChargeItemAdapter chargeItemAdapter) {
        this.setSizeFull();

        TabSheet tabSheet = new TabSheet();
        tabSheet.setSizeFull();

        tabSheet.add("Insurance", new InsuranceVIew(insuranceAdapter::findByDisplay));
        tabSheet.add("Diagnosis", new DiagnosisView(diagnosisAdapter::findByDisplay));
        tabSheet.add("Charges", new ChargeItemView(chargeItemAdapter::findByDisplay));

        add(tabSheet);
    }
}
