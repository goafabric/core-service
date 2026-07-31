package org.goafabric.core.medicalrecords.logic.jpa

import jakarta.enterprise.context.ApplicationScoped
import jakarta.enterprise.inject.spi.CDI
import jakarta.transaction.Transactional
import org.goafabric.core.medicalrecords.controller.dto.MedicalRecord
import org.goafabric.core.medicalrecords.controller.dto.MedicalRecordAble
import org.goafabric.core.medicalrecords.controller.dto.MedicalRecordDeleteAble
import org.goafabric.core.medicalrecords.controller.dto.MedicalRecordType
import org.goafabric.core.medicalrecords.logic.MedicalRecordLogic
import org.goafabric.core.medicalrecords.logic.jpa.mapper.MedicalRecordMapper
import org.goafabric.core.medicalrecords.persistence.jpa.MedicalRecordRepository

@ApplicationScoped
@Transactional
class MedicalRecordLogicJpa(
    private val mapper: MedicalRecordMapper,
    private val repository: MedicalRecordRepository
) : MedicalRecordLogic {

    override fun getById(id: String): MedicalRecord =
        mapper.map(repository.findById(id) ?: throw IllegalArgumentException("MedicalRecord not found: $id"))

    override fun save(medicalRecord: MedicalRecord): MedicalRecord =
        mapper.map(repository.save(mapper.map(medicalRecord)))

    override fun saveSpecializedRecord(specialization: String, medicalRecordAble: MedicalRecordAble): MedicalRecord =
        if (medicalRecordAble.id() != null) {
            updateSpecializedRecord(medicalRecordAble)
        } else {
            save(MedicalRecord(
                type = medicalRecordAble.recordType(),
                display = medicalRecordAble.toDisplay(),
                code = medicalRecordAble.code(),
                specialization = specialization
            ))
        }

    private fun updateSpecializedRecord(updatedRecord: MedicalRecordAble): MedicalRecord {
        val medicalRecord = mapper.map(repository.findBySpecialization(updatedRecord.id()!!))
        return save(MedicalRecord(
            id = medicalRecord.id,
            encounterId = medicalRecord.encounterId,
            version = medicalRecord.version,
            type = medicalRecord.type,
            display = updatedRecord.toDisplay(),
            code = updatedRecord.code(),
            specialization = updatedRecord.id()
        ))
    }

    override fun delete(id: String) {
        deleteSpecializedRecords(getById(id))
        repository.deleteById(id)
    }

    private fun deleteSpecializedRecords(medicalRecord: MedicalRecord) {
        medicalRecord.specialization?.let { specialization ->
            val beanClass = getClassByType(medicalRecord.type!!)
            CDI.current().select(beanClass).get().delete(specialization)
        }
    }

    private fun getClassByType(type: MedicalRecordType): Class<out MedicalRecordDeleteAble> = when (type) {
        MedicalRecordType.BODY_METRICS -> BodyMetricsLogic::class.java
        else -> throw IllegalArgumentException("Unsupported MedicalRecordType: $type")
    }
}
