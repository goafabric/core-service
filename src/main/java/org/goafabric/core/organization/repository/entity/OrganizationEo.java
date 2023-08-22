package org.goafabric.core.organization.repository.entity;

import jakarta.persistence.*;
import org.goafabric.core.organization.repository.extensions.AuditTrailListener;
import org.springframework.data.mongodb.core.mapping.Document;

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

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    public AddressEo address;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "contact_point_id", referencedColumnName = "id")
    public ContactPointEo contactPoint;

    @Version //optimistic locking
    public Long version;

}
