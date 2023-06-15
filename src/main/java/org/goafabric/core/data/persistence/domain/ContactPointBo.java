package org.goafabric.core.data.persistence.domain;

import jakarta.persistence.*;

@Entity
@Table(name="contact_point")
public class ContactPointBo {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public String id;

    public String use;
    public String system;

    @Column(name = "c_value")
    public String value;
}
