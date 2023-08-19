package org.goafabric.core.mrc.controller.vo;

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
