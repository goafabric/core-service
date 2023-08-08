package org.goafabric.core.mrc.repository.entity;

import jakarta.persistence.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "encounter")
@Document("encounter")
public class EncounterEo {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public String id;

    public String patientId;

    public LocalDate encounterDate;

    @Transient
    @org.springframework.data.annotation.Transient
    public List<AnamnesisEo> anamnesises;

    @Transient
    @org.springframework.data.annotation.Transient
    public List<ConditionEo> conditions;

    public EncounterEo() {
    }

    public EncounterEo(String id, String patientId, LocalDate encounterDate, List<AnamnesisEo> anamesises, List<ConditionEo> conditions) {
        this.id = id;
        this.patientId = patientId;
        this.encounterDate = encounterDate;
        this.conditions = conditions;
        this.anamnesises = anamesises;
    }
}
