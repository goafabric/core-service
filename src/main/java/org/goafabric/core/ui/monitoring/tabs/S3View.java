package org.goafabric.core.ui.monitoring.tabs;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;

@PageTitle("Jaeger")
public class S3View extends VerticalLayout {

    public S3View(String s3Endpoint ) {
        setSizeFull();

        this.addAttachListener(event -> {
                    if (getUI().isPresent()) {getUI().get().getPage().open(s3Endpoint, "_blank");}
                });
    }

}
