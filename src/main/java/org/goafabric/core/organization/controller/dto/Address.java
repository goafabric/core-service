package org.goafabric.core.organization.controller.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;

public record Address (
        String id,
        String version,

        @NotNull String use,
        @NotNull String street,
        @NotNull String city,
        @NotNull String postalCode,
        @NotNull String state,
        @NotNull String country
) {}

