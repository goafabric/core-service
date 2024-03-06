package org.goafabric.core.medicalrecords.persistence.jpa.entity;

import jakarta.persistence.*;
import org.goafabric.core.organization.persistence.extensions.AuditTrailListener;


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

    private String specialization;

    @Version //optimistic locking
    private Long version;

    private MedicalRecordEo() {}
    public MedicalRecordEo(String id, String encounterId, String type, String display, String code, String specialization, Long version) {
        this.id = id;
        this.encounterId = encounterId;
        this.type = type;
        this.display = display;
        this.code = code;
        this.specialization = specialization;
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

    public String getSpecialization() {
        return specialization;
    }

    public Long getVersion() {
        return version;
    }
}
