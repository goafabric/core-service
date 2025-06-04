package org.goafabric.core.organization.persistence.entity;

import jakarta.persistence.*;
import org.goafabric.core.organization.persistence.extensions.AuditTrailListener;
import org.hibernate.annotations.TenantId;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "patient")
@EntityListeners(AuditTrailListener.class)
@SuppressWarnings("java:S1104")
public class PatientEo {
    @Id
    @org.springframework.data.annotation.Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @TenantId
    private String organizationId;

    private String givenName;

    private String givenSoundex;

    private String familyName;

    private String familySoundex;

    private String gender;

    private LocalDate birthDate;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "patient_id")
    private List<AddressEo> address;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "patient_id")
    private List<ContactPointEo> contactPoint;

    @Version //optimistic locking
    private Long version;

    private PatientEo() {}

    public PatientEo(String id, String givenName, String givenSoundex, String familyName, String familySoundex, String gender, LocalDate birthDate, List<AddressEo> address, List<ContactPointEo> contactPoint, Long version) {
        this.id = id;
        this.givenName = givenName;
        this.givenSoundex = givenSoundex;
        this.familyName = familyName;
        this.familySoundex = familySoundex;
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

    public String getGivenSoundex() {
        return givenSoundex;
    }

    public String getFamilySoundex() {
        return familySoundex;
    }

    public Long getVersion() {
        return version;
    }
}
