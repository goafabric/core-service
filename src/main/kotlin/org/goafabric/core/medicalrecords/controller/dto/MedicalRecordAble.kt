package org.goafabric.core.medicalrecords.controller.dto

interface MedicalRecordAble {
    fun id(): String?
    fun recordType(): MedicalRecordType
    fun toDisplay(): String
    fun code(): String = ""
}
