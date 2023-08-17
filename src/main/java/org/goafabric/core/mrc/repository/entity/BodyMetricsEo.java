package org.goafabric.core.mrc.repository.entity;

import jakarta.persistence.*;
import org.goafabric.core.data.repository.extensions.AuditListener;
import org.springframework.data.mongodb.core.mapping.Document;


@Entity
@Table(name="body_metrics")
@Document("#{@tenantIdBean.getPrefix()body_metrics")
@EntityListeners(AuditListener.class)
public class BodyMetricsEo {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public String id;

    public String bodyHeight;
    public String bellyCircumference;
    public String headCircumference;
    public String bodyFat;

    @Version //optimistic locking
    public Long version;

}
