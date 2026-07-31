package org.goafabric.core.medicalrecords.controller.dto

enum class MedicalRecordType(val value: String) {
    ANAMNESIS("ANAMNESIS"),
    CONDITION("CONDITION"),
    CHARGEITEM("CHARGE"),
    FINDING("FINDING"),
    THERAPY("THERAPY"),
    BODY_METRICS("BODY_METRICS");
}
