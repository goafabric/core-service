package org.goafabric.core.ui.appointments;

import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.goafabric.core.ui.MainView;

@PageTitle("Appointments")
@Route(value = "appointments", layout = MainView.class)
public class AppointmentView extends VerticalLayout {
    public AppointmentView() {
        setSizeFull();
        var calendar = new DatePicker();
        calendar.setSizeFull();
        calendar.setOpened(true);
        this.add(calendar);
    }
}
