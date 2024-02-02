package org.goafabric.core.organization.controller.dto;

import java.util.List;

public record Role(
        String id,
        Long version,
        String name,
        List<Permission> permissions
) {}
