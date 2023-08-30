package org.goafabric.core.organization.controller.vo.types;

public enum PermissionType {
    Patient("Patient"),
    Practice("Practice"),
    Catalogs("Catalogs"),
    Appointments("Appointments"),
    Files("Files"),
    Monitoring("Monitoring");

    private String value;

    PermissionType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
