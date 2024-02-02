package org.goafabric.core.organization.controller.dto;

import java.util.List;

public record User(
        String id,
        Long version,
        String practitionerId,
        String name,
        List<Role> roles
) {}
