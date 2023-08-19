package org.goafabric.core.data.controller.vo;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;

public record ContactPoint (
    String id,
    @Null String version,

    @NotNull String use,
    @NotNull String system,
    @NotNull String value
) {}
