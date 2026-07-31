package org.goafabric.core.organization.persistence

import io.quarkus.hibernate.panache.PanacheRepository
import jakarta.data.repository.Query
import org.goafabric.core.organization.persistence.entity.PermissionEo

interface PermissionRepository : PanacheRepository.Managed<PermissionEo, String> {
    @Query("SELECT p FROM PermissionEo p")
    fun findAllPermissions(): List<PermissionEo>

    fun save(permissionEo: PermissionEo): PermissionEo {
        return session.merge(permissionEo)
    }

    fun saveAll(permissions: List<PermissionEo>): List<PermissionEo> {
        return permissions.map { session.merge(it) }
    }
}
