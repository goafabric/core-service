package org.goafabric.core.medicalrecords.persistence.jpa

import io.quarkus.hibernate.panache.PanacheRepository
import jakarta.data.repository.Query
import org.goafabric.core.medicalrecords.persistence.jpa.entity.EncounterEo

interface EncounterRepository : PanacheRepository.Managed<EncounterEo, String> {

    @Query("SELECT e FROM EncounterEo e JOIN FETCH e.medicalRecords m WHERE e.patientId = :patientId AND UPPER(m.display) LIKE UPPER(concat('%', :display, '%'))")
    fun findByPatientIdAndMedicalRecordsDisplayContains(patientId: String, display: String): List<EncounterEo>

    @Query("SELECT e FROM EncounterEo e JOIN FETCH e.medicalRecords m WHERE e.patientId = :patientId")
    fun findByPatientId(patientId: String): List<EncounterEo>

    fun save(encounterEo: EncounterEo): EncounterEo {
        return session.merge(encounterEo)
    }
}
