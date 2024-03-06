package org.goafabric.core.medicalrecords.persistence.jpa.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.TenantId;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "encounter")
public class EncounterEo {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @TenantId
    private String organizationId;

    private String patientId;

    private String practitionerId;

    private LocalDate encounterDate;

    private String encounterName;

    @OneToMany//(cascade = CascadeType.ALL)
    @JoinColumn(name = "encounter_id")
    private List<MedicalRecordEo> medicalRecords;

    @Version //optimistic locking
    private Long version;

    private EncounterEo() {}
    public EncounterEo(String id, String organizationId, String patientId, String practitionerId, LocalDate encounterDate, String encounterName, List<MedicalRecordEo> medicalRecords, Long version) {
        this.id = id;
        this.organizationId = organizationId;
        this.patientId = patientId;
        this.practitionerId = practitionerId;
        this.encounterDate = encounterDate;
        this.encounterName = encounterName;
        this.medicalRecords = medicalRecords;
        this.version = version;
    }

    public String getId() {
        return id;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getPractitionerId() {
        return practitionerId;
    }

    public LocalDate getEncounterDate() {
        return encounterDate;
    }

    public String getEncounterName() {
        return encounterName;
    }

    public List<MedicalRecordEo> getMedicalRecords() {
        return medicalRecords;
    }

    public Long getVersion() {
        return version;
    }
}
