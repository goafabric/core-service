package org.goafabric.core.organization.controller.vo;

import org.goafabric.core.organization.controller.vo.types.PermissionCategory;
import org.goafabric.core.organization.controller.vo.types.PermissionType;

public record Permission(
        String id,
        String version,
        PermissionCategory category,
        PermissionType type
) {}
