package org.goafabric.core.data.persistence.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "organization")
public class OrganizationBo {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public String id;

    public String name;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    public AddressBo address;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "contact_point_id", referencedColumnName = "id")
    public ContactPointBo contactPoint;

    @Version //optimistic locking
    public Long version;

}
