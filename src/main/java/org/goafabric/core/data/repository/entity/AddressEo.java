package org.goafabric.core.data.repository.entity;

import jakarta.persistence.*;
import org.goafabric.core.data.repository.extensions.AuditTrail;


@Entity
@Table(name="address")
@EntityListeners(AuditTrail.class)
public class AddressEo {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public String id;

    public String use;

    public String street;
    public String city;

    @Version //optimistic locking
    public Long version;
}
