package org.goafabric.core.organization.persistence

import io.quarkus.hibernate.panache.PanacheRepository
import jakarta.data.repository.Query
import org.goafabric.core.organization.persistence.entity.OrganizationEo

interface OrganizationRepository : PanacheRepository.Managed<OrganizationEo, String> {
    @Query("SELECT o FROM OrganizationEo o WHERE o.name LIKE CONCAT(:name, '%')")
    fun findByNameStartsWith(name: String): List<OrganizationEo>

    fun save(organizationEo: OrganizationEo): OrganizationEo {
        return session.merge(organizationEo)
    }
}
