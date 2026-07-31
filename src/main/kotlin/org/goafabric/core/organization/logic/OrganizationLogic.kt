package org.goafabric.core.organization.logic

import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional
import org.goafabric.core.organization.controller.dto.Organization
import org.goafabric.core.organization.logic.mapper.OrganizationMapper
import org.goafabric.core.organization.persistence.OrganizationRepository

@ApplicationScoped
@Transactional
class OrganizationLogic(
    private val mapper: OrganizationMapper,
    private val repository: OrganizationRepository
) {
    fun getById(id: String): Organization = mapper.map(repository.findById(id) ?: throw IllegalArgumentException("Organization not found: $id"))

    fun deleteById(id: String) = repository.deleteById(id)

    fun findByName(name: String): List<Organization> =
        mapper.map(repository.findByNameStartsWith(name))

    fun save(organization: Organization): Organization =
        mapper.map(repository.save(mapper.map(organization)))
}
