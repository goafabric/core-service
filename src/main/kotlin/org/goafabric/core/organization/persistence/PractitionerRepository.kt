package org.goafabric.core.organization.persistence

import io.quarkus.hibernate.panache.PanacheRepository
import jakarta.data.repository.Query
import org.goafabric.core.organization.persistence.entity.PractitionerEo

interface PractitionerRepository : PanacheRepository.Managed<PractitionerEo, String> {
    @Query("SELECT p FROM PractitionerEo p WHERE p.givenName LIKE CONCAT(:givenName, '%')")
    fun findByGivenNameStartsWith(givenName: String): List<PractitionerEo>

    @Query("SELECT p FROM PractitionerEo p WHERE p.familyName LIKE CONCAT(:familyName, '%')")
    fun findByFamilyNameStartsWith(familyName: String): List<PractitionerEo>

    fun save(practitionerEo: PractitionerEo): PractitionerEo {
        return session.merge(practitionerEo)
    }
}
