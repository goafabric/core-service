package org.goafabric.core.organization.logic

import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional
import org.goafabric.core.organization.controller.dto.Patient
import org.goafabric.core.organization.logic.mapper.PatientMapper
import org.goafabric.core.organization.logic.phonetic.ColognePhonetic
import org.goafabric.core.organization.persistence.PatientRepository
import org.goafabric.core.organization.persistence.entity.PatientEo

@ApplicationScoped
@Transactional
class PatientLogic(
    private val mapper: PatientMapper,
    private val repository: PatientRepository
) {
    private val phonetic = ColognePhonetic()

    fun getById(id: String): Patient = mapper.map(repository.findById(id) ?: throw IllegalArgumentException("Patient not found: $id"))

    fun deleteById(id: String) = repository.deleteById(id)

    fun findByGivenName(givenName: String): List<Patient> =
        mapper.map(repository.findByGivenNameStartsWith(givenName))

    fun findByFamilyName(familyName: String): List<Patient> =
        mapper.map(repository.findByFamilyNameStartsWith(familyName))

    fun save(patient: Patient): Patient {
        val eo = mapper.map(patient)
        val withSoundex = PatientEo(
            id = eo.id,
            givenName = eo.givenName,
            givenSoundex = phonetic.encode(eo.givenName),
            familyName = eo.familyName,
            familySoundex = phonetic.encode(eo.familyName),
            gender = eo.gender,
            birthDate = eo.birthDate,
            address = eo.address,
            contactPoint = eo.contactPoint,
            version = eo.version
        )
        return mapper.map(repository.save(withSoundex))
    }

    fun findPatientNamesByFamilyName(search: String): List<Patient> =
        mapper.map(repository.findPatientNamesByFamilyNameStartsWithIgnoreCaseOrderByFamilyName(search))

    fun findByFamilyNameAndGivenName(familyName: String, givenName: String): List<Patient> =
        mapper.map(
            repository.findByFamilyNameAndGivenName(
                familyName.lowercase(),
                phonetic.encode(familyName) ?: "",
                givenName,
                phonetic.encode(givenName) ?: ""
            )
        )
}
