package org.goafabric.core.organization.repository.entity;

import jakarta.persistence.*;
import org.goafabric.core.organization.repository.extensions.AuditTrailListener;

@Entity
@Table(name="contact_point")
//@Document("#{@tenantIdBean.getPrefix()}contact_point")
@EntityListeners(AuditTrailListener.class)
public class ContactPointEo {
    @Id
    @org.springframework.data.annotation.Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public String id;

    public String use;
    public String system;

    @Column(name = "c_value")
    public String value;
}
