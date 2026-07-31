package org.goafabric.core.organization.controller.dto

import org.goafabric.core.organization.controller.dto.types.PermissionCategory
import org.goafabric.core.organization.controller.dto.types.PermissionType

data class Permission(
    val id: String? = null,
    val version: Long? = null,
    val category: PermissionCategory? = null,
    val type: PermissionType? = null
)
