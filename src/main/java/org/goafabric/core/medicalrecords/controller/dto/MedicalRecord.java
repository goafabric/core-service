package org.goafabric.core.medicalrecords.controller.dto;

public record MedicalRecord (
        String id,
        String version,
        MedicalRecordType type,
        String display,
        String code,
        String relation) {
    public MedicalRecord(MedicalRecordType type, String display, String code) {
        this(null, null, type, display, code, null);
    }
}
