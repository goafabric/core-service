package org.goafabric.core.organization.logic

import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional
import org.goafabric.core.extensions.UserContext
import org.goafabric.core.organization.controller.dto.User
import org.goafabric.core.organization.controller.dto.UserInfo
import org.goafabric.core.organization.controller.dto.types.PermissionCategory
import org.goafabric.core.organization.controller.dto.types.PermissionType
import org.goafabric.core.organization.logic.mapper.UserMapper
import org.goafabric.core.organization.persistence.RoleRepository
import org.goafabric.core.organization.persistence.UserRepository
import org.goafabric.core.organization.persistence.entity.UserEo

@ApplicationScoped
@Transactional
class UserLogic(
    private val mapper: UserMapper,
    private val repository: UserRepository,
    private val roleRepository: RoleRepository
) {
    fun getById(id: String): User = mapper.map(repository.findById(id) ?: throw IllegalArgumentException("User not found: $id"))

    fun deleteById(id: String) = repository.deleteById(id)

    fun findByName(name: String): List<User> =
        mapper.map(repository.findByNameStartsWith(name))

    fun save(user: User): User {
        val resolvedRoles = user.roles.map { role ->
            if (role.id != null) {
                roleRepository.findById(role.id)
            } else {
                error("Role must be saved before assigning to user")
            }
        }.toMutableList()

        val userEo = UserEo(
            id = user.id,
            practitionerId = user.practitionerId,
            name = user.name,
            version = user.version,
            roles = resolvedRoles
        )
        return mapper.map(repository.save(userEo))
    }

    fun hasPermission(name: String, category: PermissionCategory, type: PermissionType): Boolean {
        val users = mapper.map(repository.findByName(name))
        if (users.size == 1) {
            for (role in users.first().roles) {
                for (permission in role.permissions) {
                    if (permission.category == category && permission.type == type) return true
                }
            }
        }
        return false
    }

    fun getUserInfo(): UserInfo =
        UserInfo(UserContext.userName, UserContext.tenantId)
}
