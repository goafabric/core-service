package org.goafabric.core.ui.monitoring.tabs;

import com.vaadin.flow.component.html.IFrame;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;

@PageTitle("Jaeger")
public class TracingView extends VerticalLayout {
    public TracingView(String tracingEndpoint) {
        setSizeFull();

        IFrame iFrame = new IFrame();
        iFrame.setSrc(tracingEndpoint);
        iFrame.setSizeFull();
        this.add(iFrame);
    }
}
