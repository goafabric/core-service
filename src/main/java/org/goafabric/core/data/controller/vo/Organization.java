package org.goafabric.core.data.controller.vo;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;

import java.util.List;

public record Organization(
    @Null String id,
    @NotNull @Size(min = 3, max = 255) String name,

    List<Address> address,
    List<ContactPoint> contactPoint
) {}
