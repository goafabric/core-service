package org.goafabric.core.mrc.controller.vo;

import java.time.LocalDate;
import java.util.List;

public record Encounter(
    String id,
    String patientId,
    LocalDate encounterDate,
    List<MedicalRecord> medicalRecords
) {}
