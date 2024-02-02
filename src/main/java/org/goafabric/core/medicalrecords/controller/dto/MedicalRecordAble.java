package org.goafabric.core.medicalrecords.controller.dto;

public interface MedicalRecordAble {
    String id();
    MedicalRecordType type();
    String toDisplay();

    default String code() { return ""; }
}
