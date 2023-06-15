package org.goafabric.core.data.persistence.domain;

import jakarta.persistence.*;
import org.hibernate.annotations.TenantId;

import java.time.LocalDate;

@Entity
@Table(name = "patient")
public class PatientBo {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public String id;

    @TenantId
    public String orgunitId;

    public String givenName;

    public String familyName;

    public String gender;

    public LocalDate birthDate;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    public AddressBo address;

    @Version //optimistic locking
    public Long version;

}
