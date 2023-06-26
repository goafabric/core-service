package org.goafabric.core.ui.catalogs;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.goafabric.core.ui.MainView;
import org.goafabric.core.ui.catalogs.tabs.ChargeItemView;
import org.goafabric.core.ui.catalogs.tabs.DiagnosisView;
import org.goafabric.core.ui.catalogs.tabs.InsuranceVIew;

import java.util.ArrayList;

@Route(value = "catalogs", layout = MainView.class)
@PageTitle("Catalogs")
public class CatalogView extends VerticalLayout {

    public CatalogView() { //(SearchLogic<Insurance> insuranceLogic, SearchLogic<Diagnosis> diagnosisCatalogLogic, SearchLogic<ChargeItem> chargeItemViewSearchLogic) {
        this.setSizeFull();

        TabSheet tabSheet = new TabSheet();
        tabSheet.setSizeFull();

        tabSheet.add("Insurance", new InsuranceVIew(search -> new ArrayList<>()));
        tabSheet.add("Diagnosis", new DiagnosisView(search -> new ArrayList<>()));
        tabSheet.add("Charges", new ChargeItemView(search -> new ArrayList<>()));

        add(tabSheet);
    }
}
