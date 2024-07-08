package org.goafabric.core.organization.persistence.extensions;

import org.goafabric.core.extensions.TenantContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
@RegisterReflectionForBinding(AuditTrailEventDispatcher.ChangeEvent.class)
public class AuditTrailEventDispatcher {
    private final ExecutorService executor = Executors.newFixedThreadPool(3);
    private final RestClient auditRestClient;
    private final String eventDispatcherUri;
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public AuditTrailEventDispatcher(RestClient.Builder builder, @Value("${event.dispatcher.uri:}") String eventDispatcherUri) {
        this.eventDispatcherUri = eventDispatcherUri;
        this.auditRestClient = createRestClient(builder);
    }

    private static RestClient createRestClient(RestClient.Builder builder) {
        var requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(1000);
        requestFactory.setReadTimeout(1000);
        return builder.requestInterceptor((request, body, execution) -> {
                    TenantContext.getAdapterHeaderMap().forEach((key, value) -> request.getHeaders().set(key, value));
                    return execution.execute(request, body);
                })
                .requestFactory(requestFactory)
                .build();
    }

    public void dispatchEvent(AuditTrailListener.AuditTrail auditTrail, Object payload) {
        if (!eventDispatcherUri.isEmpty()) {
            var changeEvent = new ChangeEvent(auditTrail.id(), TenantContext.getTenantId(), auditTrail.objectId(), auditTrail.objectType(), auditTrail.operation(), "core", payload);
            executor.submit(() -> {
                try {
                    auditRestClient.post().uri(eventDispatcherUri).contentType(MediaType.APPLICATION_JSON).body(changeEvent).retrieve();
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
            });
        }
    }

    record ChangeEvent (String id, String tenantId, String referenceId, String type, AuditTrailListener.DbOperation operation, String origin, Object payload) {}
    
}
