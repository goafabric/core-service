package org.goafabric.core.mrc.repository.entity;

import jakarta.persistence.*;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;


@Entity
@Table(name="anamnesis")
@Document("anamnesis")
//@EntityListeners(AuditTrailListener.class)
public class AnamnesisEo {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public String id;

    public String encounterId;

    @TextIndexed
    public String text;

    @Version //optimistic locking
    public Long version;

}
