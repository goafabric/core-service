package org.goafabric.core.ui;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.theme.lumo.Lumo;
import com.vaadin.flow.theme.lumo.LumoUtility;
import org.goafabric.core.crossfunctional.HttpInterceptor;
import org.goafabric.core.ui.appointments.AppointmentView;
import org.springframework.beans.factory.annotation.Value;

public class MainView extends AppLayout {

    private boolean darkness = false;
    private final boolean monitoringViewEnabled;

    public MainView(@Value("${monitoring.view.enabled:true}") boolean monitoringViewEnabled) {
        this.monitoringViewEnabled = monitoringViewEnabled;
        createHeader();
        createDrawer();
    }

    private void createHeader() {
        H1 logo = new H1("Encore");
        logo.addClassNames(
                LumoUtility.FontSize.LARGE,
                LumoUtility.Margin.MEDIUM);

        var header = new HorizontalLayout(new DrawerToggle(), createHomeButton(), createDarkToggle());

        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.setWidthFull();
        header.addClassNames(
                LumoUtility.Padding.Vertical.NONE,
                LumoUtility.Padding.Horizontal.MEDIUM);

        addToNavbar(header, createUserIcon());

    }

    private void createDrawer() {
        addToDrawer(new VerticalLayout(
                //new HorizontalLayout(new Icon(VaadinIcon.USERS), new RouterLink("Patient", PatientMainView.class)),
                //new HorizontalLayout(new Icon(VaadinIcon.HOSPITAL), new RouterLink("Practice", PracticeView.class)),
                //new HorizontalLayout(new Icon(VaadinIcon.BOOK), new RouterLink("Catalogs", CatalogView.class)),
                new HorizontalLayout(new Icon(VaadinIcon.CALENDAR_USER), new RouterLink("Appointments", AppointmentView.class))
                //new HorizontalLayout(new Icon(VaadinIcon.ARCHIVE), new RouterLink("Files", FilesView.class)),
        ));
    }

    private Button createDarkToggle() {
        Button button  = new Button(new Icon(VaadinIcon.COFFEE));
        button.addClickListener((ComponentEventListener<ClickEvent<Button>>) event -> {
            darkness = !darkness;
            getElement().executeJs("document.documentElement.setAttribute('theme', $0)", darkness ? Lumo.DARK : Lumo.LIGHT);
        });
        return button;
    }

    private Button createHomeButton() {
        var button = new Button(VaadinIcon.HOME.create());
        button.addClickListener((ComponentEventListener<ClickEvent<Button>>) event -> getUI().get().getPage().setLocation("/"));
        return button;
    }

    private HorizontalLayout createUserIcon() {
        var userButton = new Button(new Icon(VaadinIcon.USER));
        userButton.addClickListener(event -> getUI().get().getPage().open("/core/logout"));
        return new HorizontalLayout(userButton, new Label(HttpInterceptor.getUserName())
                , new Button(new Icon(VaadinIcon.HOME)), new Label(HttpInterceptor.getTenantId() + "," + HttpInterceptor.getOrgunitId()));
    }

    @Route(value = "", layout = MainView.class)
    @PageTitle("main")
    static class SubView extends VerticalLayout {
        public SubView() {
            setSizeFull();
            this.setDefaultHorizontalComponentAlignment(Alignment.CENTER);
            //this.add(new Image("/images/logo.png", ""));
        }
    }
}