package org.goafabric.core.ui.configuration.audittrail;

import jakarta.persistence.*;
import org.hibernate.annotations.TenantId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Entity
@Table(name = "audit_trail")
@Document("audit_trail")
public class AuditEvent {
    @Id
    @org.springframework.data.annotation.Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public String id;

    @TenantId
    public String orgunitId;

    public String objectType;
    public String objectId;
    public String operation;
    public String createdBy;
    public Date createdAt;
    public String modifiedBy;
    public Date modifiedAt;

    @Column(name = "oldvalue")
    public String oldValue;

    @Column(name = "newvalue")
    public String newValue;
}