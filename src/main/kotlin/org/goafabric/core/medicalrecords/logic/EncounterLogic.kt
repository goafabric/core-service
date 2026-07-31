package org.goafabric.core.medicalrecords.logic

import org.goafabric.core.medicalrecords.controller.dto.Encounter
import org.goafabric.core.medicalrecords.controller.dto.MedicalRecordType

interface EncounterLogic {
    fun save(encounter: Encounter): Encounter
    fun findByPatientIdAndDisplay(patientId: String, text: String): List<Encounter>
    fun findByPatientIdAndDisplayAndType(patientId: String, text: String, types: List<MedicalRecordType>): List<Encounter>
    fun delete(id: String)
    fun deleteAllByPatientId(patientId: String)
}
