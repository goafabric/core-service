package org.goafabric.core.organization.controller.vo.types;

public enum PermissionType {
    PATIENT("Patient"),
    PRACTICE("Practice"),
    CATALOGS("Catalogs"),
    APPOINTMENTS("Appointments"),
    FILES("Files"),
    MONITORING("Monitoring"),

    READ("READ"),
    READ_WRITE("READ_WRITE"),
    READ_WRITE_DELETE("READ_WRITE_DELETE"),
    ;

    private String value;

    PermissionType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
