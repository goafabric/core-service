package org.goafabric.core.organization.repository.extensions;

import org.goafabric.core.extensions.HttpInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
@RegisterReflectionForBinding(AuditTrailEventDispatcher.ChangeEvent.class)
public class AuditTrailEventDispatcher {
    private final ExecutorService executor = Executors.newFixedThreadPool(3);
    private final RestTemplate auditRestTemplate;
    private final String eventDispatcherUri;
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public AuditTrailEventDispatcher(RestTemplateBuilder builder, @Value("${event.dispatcher.uri:}") String eventDispatcherUri) {
        this.auditRestTemplate = builder.setConnectTimeout(Duration.ofMillis(1000)).setReadTimeout(Duration.ofMillis(1000)).build();
        this.eventDispatcherUri = eventDispatcherUri;
    }

    public void dispatchEvent(AuditTrailListener.AuditTrail auditTrail) {
        if (!eventDispatcherUri.isEmpty()) {
            var changeEvent = new ChangeEvent(auditTrail.id(), HttpInterceptor.getTenantId(), auditTrail.objectId(), auditTrail.objectType(), auditTrail.operation(), "core");
            var headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            executor.submit(() -> {
                try {
                    auditRestTemplate.postForEntity(eventDispatcherUri, new HttpEntity<>(changeEvent, headers), Void.class);
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
            });
        }
    }

    record ChangeEvent (String id, String tenantId, String referenceId, String type, AuditTrailListener.DbOperation operation, String origin) {}
    
}
