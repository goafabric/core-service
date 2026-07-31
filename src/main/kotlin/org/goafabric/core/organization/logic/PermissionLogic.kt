package org.goafabric.core.organization.logic

import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional
import org.goafabric.core.organization.controller.dto.Permission
import org.goafabric.core.organization.logic.mapper.PermissionMapper
import org.goafabric.core.organization.persistence.PermissionRepository

@ApplicationScoped
@Transactional
class PermissionLogic(
    private val mapper: PermissionMapper,
    private val repository: PermissionRepository
) {
    fun save(permission: Permission): Permission =
        mapper.map(repository.save(mapper.map(permission)))

    fun findAll(): List<Permission> =
        mapper.map(repository.findAllPermissions())

    fun saveAll(permissions: List<Permission>): List<Permission> =
        mapper.map(repository.saveAll(mapper.maps(permissions)))
}
