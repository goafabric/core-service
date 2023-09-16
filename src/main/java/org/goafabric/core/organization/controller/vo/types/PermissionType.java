package org.goafabric.core.organization.controller.vo.types;

public enum PermissionType {
    PATIENT("Patient"),
    ORGANIZATION("Organization"),
    CATALOGS("Catalogs"),
    APPOINTMENTS("Appointments"),
    FILES("Files"),
    MONITORING("Monitoring"),

    READ("Read"),
    READ_WRITE("Read & Write"),
    READ_WRITE_DELETE("Write & Delete"),
    ;

    private String value;

    PermissionType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}