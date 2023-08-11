package org.goafabric.core.mrc.repository.entity;

import jakarta.persistence.*;
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

    public String patientId;

    public LocalDate encounterDate;

    @Transient
    public List<MedicalRecordEo> medicalRecords;

    public EncounterEo() {
    }

    public EncounterEo(String id, String patientId, LocalDate encounterDate, List<MedicalRecordEo> medicalRecords) {
        this.id = id;
        this.patientId = patientId;
        this.encounterDate = encounterDate;
        this.medicalRecords = Collections.unmodifiableList(medicalRecords);
    }
}
