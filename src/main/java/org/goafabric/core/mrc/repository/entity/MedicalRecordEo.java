package org.goafabric.core.mrc.repository.entity;

import jakarta.persistence.*;
import org.goafabric.core.data.repository.extensions.AuditTrailListener;


@Entity
@Table(name="medical_record")
@EntityListeners(AuditTrailListener.class)
public class MedicalRecordEo {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public String id;

    public String type;

    public String display;

    public String code;

    public String relation;

    @Version //optimistic locking
    public Long version;

}
