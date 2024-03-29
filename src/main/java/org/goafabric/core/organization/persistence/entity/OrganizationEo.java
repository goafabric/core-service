package org.goafabric.core.organization.persistence.entity;

import jakarta.persistence.*;
import org.goafabric.core.organization.persistence.extensions.AuditTrailListener;

import java.util.List;

@Entity
@Table(name = "organization")
@EntityListeners(AuditTrailListener.class)
public class OrganizationEo {
    @Id
    @org.springframework.data.annotation.Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String name;

    private String bsnr;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "organization_id")
    public List<AddressEo> address;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "organization_id")
    public List<ContactPointEo> contactPoint;

    @Version //optimistic locking
    private Long version;

    private OrganizationEo() {}

    public OrganizationEo(String id, String name, String bsnr, List<AddressEo> address, List<ContactPointEo> contactPoint, Long version) {
        this.id = id;
        this.name = name;
        this.bsnr = bsnr;
        this.address = address;
        this.contactPoint = contactPoint;
        this.version = version;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBsnr() {
        return bsnr;
    }

    public List<AddressEo> getAddress() {
        return address;
    }

    public List<ContactPointEo> getContactPoint() {
        return contactPoint;
    }

    public Long getVersion() {
        return version;
    }
}
