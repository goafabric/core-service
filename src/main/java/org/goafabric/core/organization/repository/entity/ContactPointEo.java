package org.goafabric.core.organization.repository.entity;

import jakarta.persistence.*;
import org.goafabric.core.organization.repository.extensions.AuditTrailListener;

@Entity
@Table(name="contact_point")
@EntityListeners(AuditTrailListener.class)
public class ContactPointEo {
    @Id
    @org.springframework.data.annotation.Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String use;
    private String system;

    @Column(name = "c_value")
    private String value;

    private ContactPointEo() {}

    public ContactPointEo(String id, String use, String system, String value) {
        this.id = id;
        this.use = use;
        this.system = system;
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public String getUse() {
        return use;
    }

    public String getSystem() {
        return system;
    }

    public String getValue() {
        return value;
    }
}
