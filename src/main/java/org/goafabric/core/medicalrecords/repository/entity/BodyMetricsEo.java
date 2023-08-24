package org.goafabric.core.medicalrecords.repository.entity;

import jakarta.persistence.*;
import org.goafabric.core.organization.repository.extensions.AuditTrailListener;


@Entity
@Table(name="body_metrics")
@EntityListeners(AuditTrailListener.class)
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
