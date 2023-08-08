package org.goafabric.core.mrc.repository.entity;

import jakarta.persistence.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;


@Entity
@Table(name="condition")
@Document("condition")
//@EntityListeners(AuditTrailListener.class)
public class ConditionEo {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public String id;

    public String encounterId;

    public String code;
    public String shortname;

    //@TextIndexed
    @Indexed
    public String display;

    @Version //optimistic locking
    public Long version;

}
