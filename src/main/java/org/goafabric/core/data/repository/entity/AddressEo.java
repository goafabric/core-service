package org.goafabric.core.data.repository.entity;

import jakarta.persistence.*;
import org.goafabric.core.data.repository.extensions.AuditListener;


@Entity
@Table(name="address")
@EntityListeners(AuditListener.class)
public class AddressEo {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public String id;

    public String use;

    public String street;
    public String city;

    public String postalCode;
    public String state;
    public String country;

    @Version //optimistic locking
    public Long version;
}
