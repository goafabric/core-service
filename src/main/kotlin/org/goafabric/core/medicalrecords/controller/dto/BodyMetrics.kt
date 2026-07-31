package org.goafabric.core.medicalrecords.controller.dto

data class BodyMetrics(
    val id: String? = null,
    val version: Long? = null,
    val bodyHeight: String? = null,
    val bellyCircumference: String? = null,
    val headCircumference: String? = null,
    val bodyFat: String? = null
) : MedicalRecordAble {
    override fun id(): String? = id
    override fun recordType(): MedicalRecordType = MedicalRecordType.BODY_METRICS
    override fun toDisplay(): String =
        "Body Height: $bodyHeight Belly Circumference: $bellyCircumference Head Circumference: $headCircumference Body Fat: $bodyFat"
}
