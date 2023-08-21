package org.goafabric.core.mrc.repository.entity;

import jakarta.persistence.*;
import org.goafabric.core.data.repository.extensions.AuditListener;


@Entity
@Table(name="medical_record")
@EntityListeners(AuditListener.class)
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
