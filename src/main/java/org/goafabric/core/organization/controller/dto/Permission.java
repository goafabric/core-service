package org.goafabric.core.organization.controller.dto;

import org.goafabric.core.organization.controller.dto.types.PermissionCategory;
import org.goafabric.core.organization.controller.dto.types.PermissionType;

public record Permission(
        String id,
        Long version,
        PermissionCategory category,
        PermissionType type
) {}
