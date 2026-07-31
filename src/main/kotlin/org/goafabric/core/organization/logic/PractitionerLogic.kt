package org.goafabric.core.organization.logic

import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional
import org.goafabric.core.organization.controller.dto.Practitioner
import org.goafabric.core.organization.logic.mapper.PractitionerMapper
import org.goafabric.core.organization.persistence.PractitionerRepository

@ApplicationScoped
@Transactional
class PractitionerLogic(
    private val mapper: PractitionerMapper,
    private val repository: PractitionerRepository
) {
    fun getById(id: String): Practitioner = mapper.map(repository.findById(id) ?: throw IllegalArgumentException("Practitioner not found: $id"))

    fun deleteById(id: String) = repository.deleteById(id)

    fun findByGivenName(givenName: String): List<Practitioner> =
        mapper.map(repository.findByGivenNameStartsWith(givenName))

    fun findByFamilyName(familyName: String): List<Practitioner> =
        mapper.map(repository.findByFamilyNameStartsWith(familyName))

    fun save(practitioner: Practitioner): Practitioner =
        mapper.map(repository.save(mapper.map(practitioner)))
}
