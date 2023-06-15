package org.goafabric.core.data.controller.dto;

import jakarta.validation.constraints.NotNull;

public record Address (
        String id,

        @NotNull String use,
        @NotNull String street,
        @NotNull String city
) {}

