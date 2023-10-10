package org.goafabric.core.fhir.r4.controller.vo.identifier;

public enum IdentifierUse {
    official("official");

    private String value;

    IdentifierUse(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
