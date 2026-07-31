package org.goafabric.core.medicalrecords.logic

import org.goafabric.core.medicalrecords.controller.dto.MedicalRecord
import org.goafabric.core.medicalrecords.controller.dto.MedicalRecordAble

interface MedicalRecordLogic {
    fun getById(id: String): MedicalRecord
    fun save(medicalRecord: MedicalRecord): MedicalRecord
    fun saveSpecializedRecord(specialization: String, medicalRecordAble: MedicalRecordAble): MedicalRecord
    fun delete(id: String)
}
