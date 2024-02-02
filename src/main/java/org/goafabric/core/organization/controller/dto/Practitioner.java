package org.goafabric.core.organization.controller.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.List;

public record Practitioner(
    String id,
    Long version,
    @NotNull @Size(min = 3, max = 255) String givenName,
    @NotNull @Size(min = 3, max = 255) String familyName,

    @NotNull String gender,
    @NotNull LocalDate birthDate,
    String lanr,
    
    List<Address> address,
    List<ContactPoint> contactPoint
) {}
