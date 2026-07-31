package org.goafabric.core.medicalrecords.persistence.jpa

import io.quarkus.hibernate.panache.PanacheRepository
import jakarta.data.repository.Query
import org.goafabric.core.medicalrecords.persistence.jpa.entity.MedicalRecordEo

interface MedicalRecordRepository : PanacheRepository.Managed<MedicalRecordEo, String> {

    @Query("SELECT m FROM MedicalRecordEo m WHERE m.specialization = :specialization")
    fun findBySpecialization(specialization: String): MedicalRecordEo

    fun save(medicalRecordEo: MedicalRecordEo): MedicalRecordEo {
        return session.merge(medicalRecordEo)
    }
}
