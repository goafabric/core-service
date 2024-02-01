package org.goafabric.core.medicalrecords.controller.dto;

public interface RecordAble {
    String id();
    MedicalRecordType type();
    String toDisplay();

    default String code() { return ""; }
}
