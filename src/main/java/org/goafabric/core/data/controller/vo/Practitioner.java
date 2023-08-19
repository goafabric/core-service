package org.goafabric.core.data.controller.vo;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.List;

public record Practitioner(
    @Null String id,
    @Null String version,
    @NotNull @Size(min = 3, max = 255) String givenName,
    @NotNull @Size(min = 3, max = 255) String familyName,

    @NotNull String gender,
    @NotNull LocalDate birthDate,
    
    List<Address> address,
    List<ContactPoint> contactPoint
) {}
