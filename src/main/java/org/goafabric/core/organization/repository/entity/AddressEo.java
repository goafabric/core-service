package org.goafabric.core.organization.repository.entity;

import jakarta.persistence.*;
import org.goafabric.core.organization.repository.extensions.AuditTrailListener;
import org.springframework.data.mongodb.core.mapping.Document;


@Entity
@Table(name="address")
@Document("#{@tenantIdBean.getPrefix()}address")
@EntityListeners(AuditTrailListener.class)
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
