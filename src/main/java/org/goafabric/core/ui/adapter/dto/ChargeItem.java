package org.goafabric.core.ui.adapter.dto;

public record ChargeItem (
    String id,
    String code,
    String display,
    Double price
) {}
