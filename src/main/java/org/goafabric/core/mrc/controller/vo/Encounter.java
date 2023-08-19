package org.goafabric.core.mrc.controller.vo;

import java.time.LocalDate;
import java.util.List;

public record Encounter(
    String id,
    String version,
    String patientId,
    LocalDate encounterDate,
    String encounterName,
    List<MedicalRecord> medicalRecords
) {}
