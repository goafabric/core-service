package org.goafabric.core.data.repository.entity;

import jakarta.persistence.*;
import org.goafabric.core.data.repository.extensions.AuditTrail;

@Entity
@Table(name="contact_point")
@EntityListeners(AuditTrail.class)
public class ContactPointEo {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public String id;

    public String use;
    public String system;

    @Column(name = "c_value")
    public String value;
}
