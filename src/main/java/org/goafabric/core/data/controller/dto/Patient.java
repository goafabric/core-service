package org.goafabric.core.data.controller.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;

import java.util.List;

public record Patient(
    @Null String id,
    @NotNull @Size(min = 3, max = 255) String givenName,
    @NotNull @Size(min = 3, max = 255) String familyName,
    List<Address> address
) {}
