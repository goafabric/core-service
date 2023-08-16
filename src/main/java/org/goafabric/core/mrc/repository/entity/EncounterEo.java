package org.goafabric.core.mrc.repository.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.TenantId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "encounter")
@Document("#{@tenantIdBean.getPrefix()}encounter")
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

    public EncounterEo() {
    }

    public EncounterEo(String id, String patientId, LocalDate encounterDate, String encounterName, List<MedicalRecordEo> medicalRecords) {
        this.id = id;
        this.patientId = patientId;
        this.encounterDate = encounterDate;
        this.encounterName = encounterName;
        this.medicalRecords = Collections.unmodifiableList(medicalRecords);
    }
}
