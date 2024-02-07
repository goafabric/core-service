package org.goafabric.core.organization.repository.entity;

import jakarta.persistence.*;
import org.goafabric.core.organization.repository.extensions.AuditTrailListener;
import org.hibernate.annotations.TenantId;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "patient")
//@Document("#{@tenantIdBean.getPrefix()}patient")
@EntityListeners(AuditTrailListener.class)
public class PatientEo {
    @Id
    @org.springframework.data.annotation.Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @TenantId
    private String organizationId;

    private String givenName;

    private String familyName;

    private String gender;

    public LocalDate birthDate;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "patient_id")
    private List<AddressEo> address;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "patient_id")
    private List<ContactPointEo> contactPoint;

    @Version //optimistic locking
    private Long version;

    private PatientEo() {}

    public PatientEo(String id, String givenName, String familyName, String gender, LocalDate birthDate, List<AddressEo> address, List<ContactPointEo> contactPoint, Long version) {
        this.id = id;
        this.givenName = givenName;
        this.familyName = familyName;
        this.gender = gender;
        this.birthDate = birthDate;
        this.address = address;
        this.contactPoint = contactPoint;
        this.version = version;
    }

    public String getId() {
        return id;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public String getGivenName() {
        return givenName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public String getGender() {
        return gender;
    }

    public LocalDate getBirthDate() {
        return birthDate;
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
