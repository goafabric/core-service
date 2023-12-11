package org.goafabric.core.organization.controller.dto;

import jakarta.validation.constraints.NotNull;

public record ContactPoint (
    String id,
    String version,

    @NotNull String use,
    @NotNull String system,
    @NotNull String value
) {}
