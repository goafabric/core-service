package org.goafabric.core.organization.controller.dto;

import java.time.LocalDate;
import java.util.List;

public record Patient(
    String id,
    Long version,
    String givenName,
    String familyName,

    String gender,
    LocalDate birthDate,
    
    List<Address> address,
    List<ContactPoint> contactPoint
) {}
