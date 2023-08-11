package org.goafabric.core.mrc.controller.vo;

public record MedicalRecord (
        String id,
        MedicalRecordType type,
        String code,
        String display,
        String relations) {
    public MedicalRecord(MedicalRecordType type, String code, String display) {
        this(null, type, code, display, null);
    }
}
