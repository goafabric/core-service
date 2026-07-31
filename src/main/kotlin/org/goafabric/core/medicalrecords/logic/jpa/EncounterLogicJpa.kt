package org.goafabric.core.medicalrecords.logic.jpa

import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional
import org.goafabric.core.medicalrecords.controller.dto.Encounter
import org.goafabric.core.medicalrecords.controller.dto.MedicalRecordType
import org.goafabric.core.medicalrecords.logic.EncounterLogic
import org.goafabric.core.medicalrecords.logic.jpa.mapper.EncounterMapper
import org.goafabric.core.medicalrecords.persistence.jpa.EncounterRepository

@ApplicationScoped
@Transactional
class EncounterLogicJpa(
    private val mapper: EncounterMapper,
    private val repository: EncounterRepository,
    private val medicalRecordLogicJpa: MedicalRecordLogicJpa
) : EncounterLogic {

    override fun save(encounter: Encounter): Encounter =
        mapper.map(repository.save(mapper.map(encounter)))

    override fun findByPatientIdAndDisplayAndType(patientId: String, text: String, types: List<MedicalRecordType>): List<Encounter> {
        val encounters = findByPatientIdAndDisplay(patientId, text)
        return if (types.isNotEmpty()) {
            encounters.map { e ->
                Encounter(e.id, e.version, e.patientId, e.practitionerId, e.encounterDate, e.encounterName,
                    e.medicalRecords.filter { types.contains(it.type) }.toMutableList())
            }
        } else encounters
    }

    override fun findByPatientIdAndDisplay(patientId: String, text: String): List<Encounter> =
        if (text.isEmpty()) {
            mapper.map(repository.findByPatientId(patientId))
        } else {
            mapper.map(repository.findByPatientIdAndMedicalRecordsDisplayContains(patientId, text))
        }

    override fun delete(id: String) {
        repository.deleteById(id)
    }

    override fun deleteAllByPatientId(patientId: String) {
        findByPatientIdAndDisplay(patientId, "").forEach { encounter ->
            encounter.medicalRecords.forEach { medicalRecord -> medicalRecordLogicJpa.delete(medicalRecord.id!!) }
            delete(encounter.id!!)
        }
    }
}
