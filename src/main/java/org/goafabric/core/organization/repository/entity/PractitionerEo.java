package org.goafabric.core.organization.repository.entity;

import jakarta.persistence.*;
import org.goafabric.core.organization.repository.extensions.AuditTrailListener;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "practitioner")
@Document("#{@tenantIdBean.getPrefix()}practitioner")
@EntityListeners(AuditTrailListener.class)
public class PractitionerEo {
    @Id
    @org.springframework.data.annotation.Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public String id;

    public String givenName;

    public String familyName;

    public String gender;

    public LocalDate birthDate;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "practitioner_id")
    public List<AddressEo> address;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "practitioner_id")
    public List<ContactPointEo> contactPoint;

    @Version //optimistic locking
    public Long version;

}
