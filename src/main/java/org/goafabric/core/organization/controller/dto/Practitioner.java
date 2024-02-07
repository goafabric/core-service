package org.goafabric.core.organization.controller.dto;

import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.List;

public record Practitioner(
    String id,
    Long version,
    String givenName,
    String familyName,

    String gender,
    LocalDate birthDate,
    String lanr,
    
    List<Address> address,
    List<ContactPoint> contactPoint
) {}
