package org.goafabric.core.medicalrecords.persistence.jpa.entity;

import jakarta.persistence.*;
import org.goafabric.core.organization.persistence.extensions.AuditTrailListener;


@Entity
@Table(name="body_metrics")
//@Document("#{@tenantIdBean.getPrefix()}body_metrics")
@EntityListeners(AuditTrailListener.class)
public class BodyMetricsEo {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String bodyHeight;
    private String bellyCircumference;
    private String headCircumference;
    private String bodyFat;

    @Version //optimistic locking
    private Long version;

    private BodyMetricsEo() {}

    public BodyMetricsEo(String id, String bodyHeight, String bellyCircumference, String headCircumference, String bodyFat, Long version) {
        this.id = id;
        this.bodyHeight = bodyHeight;
        this.bellyCircumference = bellyCircumference;
        this.headCircumference = headCircumference;
        this.bodyFat = bodyFat;
        this.version = version;
    }

    public String getId() {
        return id;
    }

    public String getBodyHeight() {
        return bodyHeight;
    }

    public String getBellyCircumference() {
        return bellyCircumference;
    }

    public String getHeadCircumference() {
        return headCircumference;
    }

    public String getBodyFat() {
        return bodyFat;
    }

    public Long getVersion() {
        return version;
    }
}
