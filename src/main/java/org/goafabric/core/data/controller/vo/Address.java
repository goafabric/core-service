package org.goafabric.core.data.controller.vo;

import jakarta.validation.constraints.NotNull;

public record Address (
        String id,

        @NotNull String use,
        @NotNull String street,
        @NotNull String city,
        @NotNull String postalCode,
        @NotNull String state,
        @NotNull String country
) {}

