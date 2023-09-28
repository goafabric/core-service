package org.goafabric.core.organization.repository.entity;

import jakarta.persistence.*;
import org.goafabric.core.organization.repository.extensions.AuditTrailListener;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Entity
@Table(name = "organization")
@Document("#{@tenantIdBean.getPrefix()}organization")
@EntityListeners(AuditTrailListener.class)
public class OrganizationEo {
    @Id
    @org.springframework.data.annotation.Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public String id;

    public String name;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "organization_id")
    public List<AddressEo> address;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "organization_id")
    public List<ContactPointEo> contactPoint;

    @Version //optimistic locking
    public Long version;

}
