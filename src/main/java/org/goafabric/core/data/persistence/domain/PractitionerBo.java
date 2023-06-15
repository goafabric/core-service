package org.goafabric.core.data.persistence.domain;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "practitioner")
public class PractitionerBo {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public String id;

    public String givenName;

    public String familyName;

    public String gender;

    public LocalDate birthDate;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    public AddressBo address;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "contact_point_id", referencedColumnName = "id")
    public ContactPointBo contactPoint;

    @Version //optimistic locking
    public Long version;

}
