package org.goafabric.core.ui.adapter.vo;

public record ChargeItem (
    String id,
    String code,
    String display,
    Double price
) {}
