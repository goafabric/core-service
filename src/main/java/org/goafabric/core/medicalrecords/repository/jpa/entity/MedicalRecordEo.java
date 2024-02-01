package org.goafabric.core.medicalrecords.repository.jpa.entity;

import jakarta.persistence.*;
import org.goafabric.core.organization.repository.extensions.AuditTrailListener;


@Entity
@Table(name="medical_record")
@EntityListeners(AuditTrailListener.class)
public class MedicalRecordEo {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "encounter_id")
    private String encounterId;

    private String type;

    private String display;

    private String code;

    private String relation;

    @Version //optimistic locking
    private Long version;

    private MedicalRecordEo() {}
    public MedicalRecordEo(String id, String encounterId, String type, String display, String code, String relation, Long version) {
        this.id = id;
        this.encounterId = encounterId;
        this.type = type;
        this.display = display;
        this.code = code;
        this.relation = relation;
        this.version = version;
    }

    public String getId() {
        return id;
    }

    public String getEncounterId() {
        return encounterId;
    }

    public String getType() {
        return type;
    }

    public String getDisplay() {
        return display;
    }

    public String getCode() {
        return code;
    }

    public String getRelation() {
        return relation;
    }

    public Long getVersion() {
        return version;
    }
}
