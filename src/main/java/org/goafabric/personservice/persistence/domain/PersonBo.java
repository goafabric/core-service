package org.goafabric.personservice.persistence.domain;

import jakarta.persistence.*;
import org.hibernate.annotations.TenantId;

@Entity
@Table(name = "person")
public class PersonBo {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public String id;

    @TenantId
    public String companyId;

    public String firstName;

    public String lastName;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    public AddressBo address;

    @Version //optimistic locking
    public Long version;

}
