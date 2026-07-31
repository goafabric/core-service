package org.goafabric.core.medicalrecords.logic.jpa

import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional
import org.goafabric.core.medicalrecords.controller.dto.BodyMetrics
import org.goafabric.core.medicalrecords.controller.dto.MedicalRecord
import org.goafabric.core.medicalrecords.controller.dto.MedicalRecordDeleteAble
import org.goafabric.core.medicalrecords.logic.MedicalRecordLogic
import org.goafabric.core.medicalrecords.logic.jpa.mapper.BodyMetricsMapper
import org.goafabric.core.medicalrecords.persistence.jpa.BodyMetricsRepository

@ApplicationScoped
@Transactional
class BodyMetricsLogic(
    private val mapper: BodyMetricsMapper,
    private val repository: BodyMetricsRepository,
    private val medicalRecordLogic: MedicalRecordLogic
) : MedicalRecordDeleteAble {

    fun getById(id: String): BodyMetrics =
        mapper.map(repository.findById(id) ?: throw IllegalArgumentException("BodyMetrics not found: $id"))

    fun save(bodyMetrics: BodyMetrics): MedicalRecord {
        val saved = repository.save(mapper.map(bodyMetrics))
        return medicalRecordLogic.saveSpecializedRecord(saved.id!!, bodyMetrics)
    }

    override fun delete(id: String) {
        repository.deleteById(id)
    }
}
