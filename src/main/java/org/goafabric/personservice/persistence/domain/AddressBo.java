package org.goafabric.personservice.persistence.domain;

import jakarta.persistence.*;
import org.hibernate.annotations.TenantId;


@Entity
@Table(name="address")
public class AddressBo  {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public String id;

    @TenantId
    public String companyId;

    public String street;
    public String city;

    @Version //optimistic locking
    public Long version;
}
