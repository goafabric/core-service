package org.goafabric.core.medicalrecords.controller.dto

data class MedicalRecord(
    var id: String? = null,
    var encounterId: String? = null,
    var version: Long? = null,
    var type: MedicalRecordType? = null,
    var display: String? = null,
    var code: String? = null,
    var specialization: String? = null
) : MedicalRecordAble {
    override fun id(): String? = id
    override fun recordType(): MedicalRecordType = type!!
    override fun toDisplay(): String = display ?: ""
    override fun code(): String = code ?: ""

    companion object {
        operator fun invoke(type: MedicalRecordType, display: String, code: String): MedicalRecord =
            MedicalRecord(id = null, encounterId = null, version = null, type = type, display = display, code = code, specialization = null)

        operator fun invoke(type: MedicalRecordType, display: String, code: String, specialization: String?): MedicalRecord =
            MedicalRecord(id = null, encounterId = null, version = null, type = type, display = display, code = code, specialization = specialization)
    }
}
