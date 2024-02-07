package org.goafabric.core.organization.controller.dto;

import java.util.List;

public record Organization(
    String id,
    Long version,
    String name,
    String bsnr,

    List<Address> address,
    List<ContactPoint> contactPoint
) {}
