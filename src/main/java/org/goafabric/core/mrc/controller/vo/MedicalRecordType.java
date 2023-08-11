package org.goafabric.core.mrc.controller.vo;

public enum MedicalRecordType {

    ANAMNESIS("ANAMNESIS"),
    CONDITION("CONDTION"),
    CHARGEITEM("CHARGE"),
    FINDING("FINDING"),
    THERAPY("THERAPY")
    ;
    
    private String value;

    MedicalRecordType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}