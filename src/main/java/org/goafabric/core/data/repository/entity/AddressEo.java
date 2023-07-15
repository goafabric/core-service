package org.goafabric.core.data.repository.entity;

import jakarta.persistence.*;
import org.goafabric.core.data.repository.extensions.AuditListener;
import org.springframework.data.mongodb.core.mapping.Document;


@Entity
@Table(name="address")
@Document("address")
@EntityListeners(AuditListener.class)
public class AddressEo {
    @Id
    @org.springframework.data.annotation.Id
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
