package org.goafabric.core.organization.persistence.entity;

import jakarta.persistence.*;
import org.goafabric.core.organization.persistence.extensions.AuditTrailListener;

@Entity
@Table(name = "permission")
@EntityListeners(AuditTrailListener.class)
public class PermissionEo {
    @Id
    @org.springframework.data.annotation.Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String category;

    private String type;

    @Version //optimistic locking
    private Long version;

    private PermissionEo() {}

    public PermissionEo(String id, String category, String type, Long version) {
        this.id = id;
        this.category = category;
        this.type = type;
        this.version = version;
    }

    public String getId() {
        return id;
    }

    public String getCategory() {
        return category;
    }

    public String getType() {
        return type;
    }

    public Long getVersion() {
        return version;
    }
}
