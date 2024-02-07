package org.goafabric.core.organization.controller.dto;

public record ContactPoint (
    String id,
    Long version,

    String use,
    String system,
    String value
) {}
