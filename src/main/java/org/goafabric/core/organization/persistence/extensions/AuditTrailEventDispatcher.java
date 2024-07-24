package org.goafabric.core.organization.persistence.extensions;

import org.goafabric.core.extensions.TenantContext;
import org.goafabric.core.medicalrecords.persistence.jpa.entity.MedicalRecordEo;
import org.goafabric.event.EventData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class AuditTrailEventDispatcher {
    private final ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final KafkaTemplate kafkaTemplate;
    private final String kafkaServers;
    private final RestClient auditRestClient;
    private final String eventDispatcherUri;

    public AuditTrailEventDispatcher(KafkaTemplate kafkaTemplate, @Value("${spring.kafka.bootstrap-servers:}") String kafkaServers,
                                        RestClient.Builder builder, @Value("${event.dispatcher.uri:}") String eventDispatcherUri) {
        this.kafkaTemplate = kafkaTemplate;
        this.kafkaServers = kafkaServers;
        this.eventDispatcherUri = eventDispatcherUri;
        this.auditRestClient = createRestClient(builder);
    }

    public void dispatchEvent(AuditTrailListener.AuditTrail auditTrail, Object payload) {
        if (!kafkaServers.isEmpty()) {
            executor.submit(() -> {
                String topic = payload instanceof MedicalRecordEo medicalRecordEo ? medicalRecordEo.getType().toLowerCase() : auditTrail.objectType();
                log.info("prdoducing event for topic {}", topic);
                kafkaTemplate.send(topic, auditTrail.objectId(), new EventData(TenantContext.getAdapterHeaderMap(), auditTrail.objectId(), auditTrail.operation().toString(), payload));
            });
        }
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

    record ChangeEvent (String id, String tenantId, String referenceId, String type, AuditTrailListener.DbOperation operation, String origin, Object payload) {}

}
