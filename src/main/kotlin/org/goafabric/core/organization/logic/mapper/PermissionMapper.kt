package org.goafabric.core.organization.logic.mapper

import org.goafabric.core.organization.controller.dto.Permission
import org.goafabric.core.organization.persistence.entity.PermissionEo
import org.mapstruct.Mapper
import org.mapstruct.ReportingPolicy

@Mapper(componentModel = "cdi", unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface PermissionMapper {
    fun map(value: PermissionEo): Permission
    fun map(value: Permission): PermissionEo
    fun map(values: List<PermissionEo>): List<Permission>
    fun maps(values: List<Permission>): List<PermissionEo>
}
