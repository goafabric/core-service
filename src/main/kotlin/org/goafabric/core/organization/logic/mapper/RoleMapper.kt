package org.goafabric.core.organization.logic.mapper

import org.goafabric.core.organization.controller.dto.Role
import org.goafabric.core.organization.persistence.entity.RoleEo
import org.mapstruct.Mapper
import org.mapstruct.ReportingPolicy

@Mapper(componentModel = "cdi", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = [PermissionMapper::class])
interface RoleMapper {
    fun map(value: RoleEo): Role
    fun map(value: Role): RoleEo
    fun map(values: List<RoleEo>): List<Role>
}
