package org.goafabric.core.organization.logic

import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional
import org.goafabric.core.organization.controller.dto.Role
import org.goafabric.core.organization.logic.mapper.PermissionMapper
import org.goafabric.core.organization.logic.mapper.RoleMapper
import org.goafabric.core.organization.persistence.PermissionRepository
import org.goafabric.core.organization.persistence.RoleRepository
import org.goafabric.core.organization.persistence.entity.RoleEo

@ApplicationScoped
@Transactional
class RoleLogic(
    private val mapper: RoleMapper,
    private val permissionMapper: PermissionMapper,
    private val repository: RoleRepository,
    private val permissionRepository: PermissionRepository
) {
    fun getById(id: String): Role = mapper.map(repository.findById(id) ?: throw IllegalArgumentException("Role not found: $id"))

    fun deleteById(id: String) = repository.deleteById(id)

    fun findByName(name: String): List<Role> =
        mapper.map(repository.findByNameStartsWith(name))

    fun save(role: Role): Role {
        val resolvedPermissions = role.permissions.map { perm ->
            if (perm.id != null) {
                permissionRepository.findById(perm.id)
            } else {
                permissionRepository.save(permissionMapper.map(perm))
            }
        }.toMutableList()

        val roleEo = RoleEo(
            id = role.id,
            name = role.name,
            version = role.version,
            permissions = resolvedPermissions
        )
        return mapper.map(repository.save(roleEo))
    }
}
