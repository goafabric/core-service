package org.goafabric.core.organization.persistence

import io.quarkus.hibernate.panache.PanacheRepository
import jakarta.data.repository.Find
import jakarta.data.repository.Query
import org.goafabric.core.organization.persistence.entity.UserEo

interface UserRepository : PanacheRepository.Managed<UserEo, String> {
    @Find
    fun findByName(name: String): List<UserEo>

    @Query("SELECT u FROM UserEo u WHERE u.name LIKE CONCAT(:name, '%')")
    fun findByNameStartsWith(name: String): List<UserEo>

    fun save(userEo: UserEo): UserEo {
        return session.merge(userEo)
    }
}
