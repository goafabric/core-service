package org.goafabric.core.data.repository.entity;

import jakarta.persistence.*;
import org.goafabric.core.data.repository.extensions.AuditListener;

@Entity
@Table(name = "organization")
@EntityListeners(AuditListener.class)
public class OrganizationEo {
    @Id
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
