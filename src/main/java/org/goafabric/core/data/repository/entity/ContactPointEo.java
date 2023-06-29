package org.goafabric.core.data.repository.entity;

import jakarta.persistence.*;

@Entity
@Table(name="contact_point")
public class ContactPointEo {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public String id;

    public String use;
    public String system;

    @Column(name = "c_value")
    public String value;
}
