package org.goafabric.core.medicalrecords.controller.dto;

import org.goafabric.core.medicalrecords.logic.MedicalRecordDeleteAble;
import org.goafabric.core.medicalrecords.logic.jpa.BodyMetricsLogic;

public enum MedicalRecordType {

    ANAMNESIS("ANAMNESIS"),
    CONDITION("CONDITION"),
    CHARGEITEM("CHARGE"),
    FINDING("FINDING"),
    THERAPY("THERAPY"),
    BODY_METRICS("BODY_METRICS"),
    ;
    
    private String value;

    MedicalRecordType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static Class<? extends MedicalRecordDeleteAble> getClassByType(MedicalRecordType type) {
        return switch (type) {
            case BODY_METRICS -> BodyMetricsLogic.class;
            default -> throw new IllegalArgumentException("Unsupported MedicalRecordType: " + type);
        };
    }

}
