package org.goafabric.core.medicalrecords.controller.dto

import java.time.LocalDate

data class Encounter(
    var id: String? = null,
    var version: Long? = null,
    var patientId: String? = null,
    var practitionerId: String? = null,
    var encounterDate: LocalDate? = null,
    var encounterName: String? = null,
    var medicalRecords: MutableList<MedicalRecord> = mutableListOf()
)
