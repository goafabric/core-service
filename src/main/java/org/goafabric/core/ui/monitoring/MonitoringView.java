package org.goafabric.core.ui.monitoring;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.goafabric.core.ui.MainView;
import org.goafabric.core.ui.adapter.AuditTrailRepository;
import org.goafabric.core.ui.monitoring.tabs.AuditTrailView;
import org.goafabric.core.ui.monitoring.tabs.LokiView;
import org.goafabric.core.ui.monitoring.tabs.S3View;
import org.goafabric.core.ui.monitoring.tabs.TracingView;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.HealthEndpoint;

@Route(value = "monitoring", layout = MainView.class)
@PageTitle("Monitoring")
public class MonitoringView extends VerticalLayout {

    public MonitoringView(HealthEndpoint healthEndpoint,
                          @Value("${monitoring.view.tracing.url}") String monitoringViewTracingUrl,
                          @Value("${monitoring.view.loki.url}") String monitoringViewLokiUrl,
                          @Value("${monitoring.view.s3.url}") String monitoringViewS3Url,
                          AuditTrailRepository auditTrailRepository
                          ) {
        this.setSizeFull();

        TabSheet tabSheet = new TabSheet();
        tabSheet.setSizeFull();

        tabSheet.add("Tracing", new TracingView(monitoringViewTracingUrl));
        tabSheet.add("Loki", new LokiView(monitoringViewLokiUrl));
        tabSheet.add("S3", new S3View(monitoringViewS3Url));
        tabSheet.add("Audit", new AuditTrailView(search -> auditTrailRepository.findAll()));

        add(tabSheet);
    }
}
