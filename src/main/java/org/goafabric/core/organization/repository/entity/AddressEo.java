package org.goafabric.core.organization.repository.entity;

import jakarta.persistence.*;
import org.goafabric.core.organization.repository.extensions.AuditTrailListener;


@Entity
@Table(name="address")
//@Document("#{@tenantIdBean.getPrefix()}address")
@EntityListeners(AuditTrailListener.class)
public class AddressEo {
    @Id
    @org.springframework.data.annotation.Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String use;

    private String street;
    private String city;

    private String postalCode;
    private String state;
    private String country;

    @Version //optimistic locking
    private Long version;

    private AddressEo() {};

    public AddressEo(String id, String use, String street, String city, String postalCode, String state, String country, Long version) {
        this.id = id;
        this.use = use;
        this.street = street;
        this.city = city;
        this.postalCode = postalCode;
        this.state = state;
        this.country = country;
        this.version = version;
    }

    public String getId() {
        return id;
    }

    public String getUse() {
        return use;
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getState() {
        return state;
    }

    public String getCountry() {
        return country;
    }

    public Long getVersion() {
        return version;
    }
}
