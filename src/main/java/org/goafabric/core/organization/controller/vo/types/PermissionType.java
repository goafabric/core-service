package org.goafabric.core.organization.controller.vo.types;

public enum PermissionType {
    MONITORING("Monitoring");

    private String value;

    PermissionType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
