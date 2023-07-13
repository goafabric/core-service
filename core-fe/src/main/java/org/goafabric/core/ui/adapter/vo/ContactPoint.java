package org.goafabric.core.ui.adapter.vo;

import jakarta.validation.constraints.NotNull;

public record ContactPoint (
    String id,

    @NotNull String use,
    @NotNull String system,
    @NotNull String value
) {}
