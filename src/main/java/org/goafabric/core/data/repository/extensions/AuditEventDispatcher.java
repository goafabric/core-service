package org.goafabric.core.data.repository.extensions;

import org.goafabric.core.extensions.HttpInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class AuditEventDispatcher {
    @Autowired
    private RestTemplate auditRestTemplate;

    @Value("${event.dispatcher.uri:}")
    private String eventDispatcherUri;

    private final ExecutorService executor = Executors.newFixedThreadPool(3);

    record ChangeEvent (String id, String tenantId, String referenceId, String type, AuditTrailListener.DbOperation operation, String origin) {}

    public  void dispatchEvent(AuditTrailListener.AuditTrail auditTrail) {
        if (!eventDispatcherUri.isEmpty()) {
            var changeEvent = new ChangeEvent(auditTrail.id(), HttpInterceptor.getTenantId(), auditTrail.objectId(), auditTrail.objectType(), auditTrail.operation(), "core");
            var headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            executor.submit(() -> {
                auditRestTemplate.postForEntity(eventDispatcherUri, new HttpEntity<>(changeEvent, headers), Void.class); });
        }
    }

    @Bean
    public RestTemplate auditRestTemplate(RestTemplateBuilder builder) {
        return builder.setConnectTimeout(Duration.ofMillis(1000)).setReadTimeout(Duration.ofMillis(1000)).build();
    }
}
