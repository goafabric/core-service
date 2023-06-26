package org.goafabric.core.ui.adapter.dto;

public record Diagnosis (
    String id,
    String code,
    String display,
    String shortname
) {}
