package org.goafabric.core.data.persistence.domain;

import jakarta.persistence.*;


@Entity
@Table(name="address")
public class AddressBo  {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public String id;

    public String use;

    public String street;
    public String city;

    @Version //optimistic locking
    public Long version;
}
