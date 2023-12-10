package org.goafabric.core.medicalrecords.controller.dto;

import java.time.LocalDate;
import java.util.List;

public record Encounter(
    String id,
    String version,
    String patientId,
    String practitionerId,
    LocalDate encounterDate,
    String encounterName,
    List<MedicalRecord> medicalRecords
) {}
