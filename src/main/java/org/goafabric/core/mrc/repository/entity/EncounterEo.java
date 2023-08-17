package org.goafabric.core.mrc.repository.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.TenantId;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "encounter")
public class EncounterEo {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public String id;

    @TenantId
    public String orgunitId;

    public String patientId;

    public LocalDate encounterDate;

    public String encounterName;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "encounter_id")
    public List<MedicalRecordEo> medicalRecords;

    @Version //optimistic locking
    public Long version;

}
