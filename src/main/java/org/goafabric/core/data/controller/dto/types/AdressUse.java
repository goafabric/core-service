package org.goafabric.core.data.controller.dto.types;

public enum AdressUse {
    /**
     * A communication address at a home.
     */
    HOME("home"),
    /**
     * An office address. First choice for business related contacts during business hours.
     */
    WORK("work"),
    /**
     * A temporary address. The period can provide more detailed information.
     */
    TEMP("temp"),
    /**
     * This address is no longer in use (or was never correct but retained for records).
     */
    OLD("old"),
    /**
     * An address to be used to send bills, invoices, receipts etc.
     */
    BILLING("billing"),
    /**
     * added to help the parsers with the generic types
     */
    NULL(null);

    private String value;

    AdressUse(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
