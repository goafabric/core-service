package org.goafabric.core.organization.controller.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;

import java.util.List;

public record Organization(
    String id,
    Long version,
    @NotNull @Size(min = 3, max = 255) String name,
    String bsnr,

    List<Address> address,
    List<ContactPoint> contactPoint
) {}
