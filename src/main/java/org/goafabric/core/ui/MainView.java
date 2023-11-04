package org.goafabric.core.ui;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.NativeLabel;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.function.SerializableConsumer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.theme.lumo.Lumo;
import com.vaadin.flow.theme.lumo.LumoUtility;
import org.goafabric.core.extensions.HttpInterceptor;
import org.goafabric.core.organization.repository.extensions.TenantResolver;
import org.goafabric.core.ui.appointments.AppointmentView;
import org.goafabric.core.ui.catalogs.CatalogView;
import org.goafabric.core.ui.extension.UserHolder;
import org.goafabric.core.ui.files.FilesView;
import org.goafabric.core.ui.monitoring.MonitoringView;
import org.goafabric.core.ui.mrc.MRCMainView;
import org.goafabric.core.ui.practice.Organization;
import org.springframework.beans.factory.annotation.Value;

import java.net.URL;

public class MainView extends AppLayout {

    private boolean darkness = false;
    private final boolean monitoringViewEnabled;

    public MainView(@Value("${monitoring.view.enabled:true}") boolean monitoringViewEnabled) {
        this.monitoringViewEnabled = monitoringViewEnabled;
        /*
        VaadinSession.getCurrent().addRequestHandler(
                new RequestHandler() {
                    @Override
                    public boolean handleRequest(VaadinSession session, VaadinRequest request, VaadinResponse response) throws IOException {
                        HttpInterceptor.prehandle(((VaadinServletRequest) request).getHttpServletRequest());
                        return false;
                    }
                });

         */
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
        var layout = new VerticalLayout();

        if (UserHolder.userHasPermission("Patient")) {
            layout.add(new HorizontalLayout(new Icon(VaadinIcon.USERS), new RouterLink("Patient", MRCMainView.class)));
        }
        if (UserHolder.userHasPermission("Organization")) {
            layout.add(new HorizontalLayout(new Icon(VaadinIcon.HOSPITAL), new RouterLink("Organization", Organization.class)));
        }
        if (UserHolder.userHasPermission("Catalogs")) {
            layout.add(new HorizontalLayout(new Icon(VaadinIcon.BOOK), new RouterLink("Catalogs", CatalogView.class)));
        }
        if (UserHolder.userHasPermission("Appointments")) {
            layout.add(new HorizontalLayout(new Icon(VaadinIcon.CALENDAR_USER), new RouterLink("Appointments", AppointmentView.class)));
        }
        if (UserHolder.userHasPermission("Files")) {
            layout.add(new HorizontalLayout(new Icon(VaadinIcon.ARCHIVE), new RouterLink("Files", FilesView.class)));
        }
        if (UserHolder.userHasPermission("Monitoring")) {
            layout.add(new HorizontalLayout(new Icon(VaadinIcon.CHART), new RouterLink("Monitoring", MonitoringView.class)));
        }
        addToDrawer(layout);
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
        button.addClickListener((ComponentEventListener<ClickEvent<Button>>) event -> getUI().get().getPage().setLocation("./"));
        return button;
    }

    private HorizontalLayout createUserIcon() {
        var userButton = new Button(new Icon(VaadinIcon.USER));
        userButton.addClickListener(event -> {
            var page = getUI().get().getPage();
            page.fetchCurrentURL((SerializableConsumer<URL>) url ->
                    page.open(url.getPath().contains("/core") ? "/core/logout" : "/logout", "_self"));
        });
        return new HorizontalLayout(userButton, new NativeLabel(UserHolder.getUser().name())
                , new Button(new Icon(VaadinIcon.HOME)), new NativeLabel(HttpInterceptor.getTenantId() + "," + TenantResolver.getOrgunitId()));
    }

    @Route(value = "", layout = MainView.class)
    @PageTitle("main")
    static class SubView extends VerticalLayout {
        public SubView() {
            setSizeFull();
            this.setDefaultHorizontalComponentAlignment(Alignment.CENTER);
            if (HttpInterceptor.getTenantId().equals("0")) {
                this.add(new Text("Hibbert's Hospital"));
                this.add(new Image("./images/hibbert.jpg", ""));
            } else {
                this.add(new Text("Zoidberg's Practice"));
                this.add(new Image("./images/zoidberg.jpg", ""));
            }
        }
    }
}