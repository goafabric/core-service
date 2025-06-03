package org.goafabric.core.organization.persistence.extensions;

import org.goafabric.core.extensions.UserContext;
import org.goafabric.core.medicalrecords.persistence.jpa.entity.MedicalRecordEo;
import org.goafabric.event.EventData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RegisterReflectionForBinding(EventData.class)
public class AuditTrailEventDispatcher {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final KafkaTemplate kafkaTemplate;
    private final String kafkaServers;

    public AuditTrailEventDispatcher(KafkaTemplate kafkaTemplate, @Value("${spring.kafka.bootstrap-servers:}") String kafkaServers) {
        this.kafkaTemplate = kafkaTemplate;
        this.kafkaServers = kafkaServers;
    }

    public void dispatchEvent(AuditTrailListener.AuditTrail auditTrail, Object payload) {
        if (!kafkaServers.isEmpty()) {
            String topic = payload instanceof MedicalRecordEo medicalRecordEo ? medicalRecordEo.getType().toLowerCase() : auditTrail.objectType();
            log.info("producing event for topic {}", topic);
            kafkaTemplate.send(topic, auditTrail.objectId(), new EventData(UserContext.getAdapterHeaderMap(), auditTrail.objectId(), auditTrail.operation().toString(), payload));
        }
    }

}
