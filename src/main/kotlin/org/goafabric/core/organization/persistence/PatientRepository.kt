package org.goafabric.core.organization.persistence

import io.quarkus.hibernate.panache.PanacheRepository
import jakarta.data.repository.Query
import org.goafabric.core.organization.persistence.entity.PatientEo

interface PatientRepository : PanacheRepository.Managed<PatientEo, String> {
    @Query("SELECT p FROM PatientEo p WHERE p.givenName LIKE CONCAT(:givenName, '%')")
    fun findByGivenNameStartsWith(givenName: String): List<PatientEo>

    @Query("SELECT p FROM PatientEo p WHERE p.familyName LIKE CONCAT(:familyName, '%')")
    fun findByFamilyNameStartsWith(familyName: String): List<PatientEo>

    @Query("SELECT p FROM PatientEo p WHERE LOWER(p.familyName) LIKE LOWER(CONCAT(:familyName, '%')) ORDER BY p.familyName")
    fun findPatientNamesByFamilyNameStartsWithIgnoreCaseOrderByFamilyName(familyName: String): List<PatientEo>

    @Query(
        "SELECT p FROM PatientEo p " +
        "WHERE (UPPER(p.familyName) LIKE UPPER(CONCAT('%', :familyName, '%')) OR p.familySoundex = :familySoundex) " +
        "AND (UPPER(p.givenName) LIKE UPPER(CONCAT('%', :givenName, '%')) OR p.givenSoundex = :givenSoundex)"
    )
    fun findByFamilyNameAndGivenName(
        familyName: String, familySoundex: String, givenName: String, givenSoundex: String
    ): List<PatientEo>

    fun save(patientEo: PatientEo): PatientEo {
        return session.merge(patientEo)
    }
}
