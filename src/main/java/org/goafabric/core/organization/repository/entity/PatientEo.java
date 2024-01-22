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
    public String id;

    @TenantId
    public String orgunitId;

    public String givenName;

    public String familyName;

    public String gender;

    public LocalDate birthDate;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "patient_id")
    public List<AddressEo> address;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "patient_id")
    public List<ContactPointEo> contactPoint;

    @Version //optimistic locking
    public Long version;

    public String getId() {
        return id;
    }

    public String getFamilyName() {
        return familyName;
    }
}
