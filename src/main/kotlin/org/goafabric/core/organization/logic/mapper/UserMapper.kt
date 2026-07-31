package org.goafabric.core.organization.logic.mapper

import org.goafabric.core.organization.controller.dto.User
import org.goafabric.core.organization.persistence.entity.UserEo
import org.mapstruct.Mapper
import org.mapstruct.ReportingPolicy

@Mapper(componentModel = "cdi", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = [RoleMapper::class, PermissionMapper::class])
interface UserMapper {
    fun map(value: UserEo): User
    fun map(value: User): UserEo
    fun map(values: List<UserEo>): List<User>
}
