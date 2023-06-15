package org.goafabric.core.data.controller.dto;

import jakarta.validation.constraints.NotNull;

public record ContactPoint (
    String id,

    @NotNull String use,
    @NotNull String system,
    @NotNull String value
) {}
