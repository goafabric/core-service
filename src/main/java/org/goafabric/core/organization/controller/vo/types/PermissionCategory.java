package org.goafabric.core.organization.controller.vo.types;

public enum PermissionCategory {
    VIEW("VIEW"),
    CRUD("CRUD");

    private String value;

    PermissionCategory(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
