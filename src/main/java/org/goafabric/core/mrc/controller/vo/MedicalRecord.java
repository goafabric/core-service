package org.goafabric.core.mrc.controller.vo;

public record MedicalRecord (
        String id,
        String type,
        String code,
        String display,
        String relations) {
    public MedicalRecord(String type, String code, String display) {
        this(null, type, code, display, null);
    }
}
