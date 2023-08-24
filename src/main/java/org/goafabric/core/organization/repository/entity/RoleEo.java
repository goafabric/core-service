package org.goafabric.core.organization.repository.entity;

import jakarta.persistence.*;
import org.goafabric.core.organization.repository.extensions.AuditTrailListener;

@Entity
@Table(name = "roles")
@EntityListeners(AuditTrailListener.class)
public class RoleEo {
    @Id
    @org.springframework.data.annotation.Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public String id;

    public String name;

    @Version //optimistic locking
    public Long version;

}
