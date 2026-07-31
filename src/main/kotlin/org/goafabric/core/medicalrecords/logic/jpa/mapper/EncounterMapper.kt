package org.goafabric.core.medicalrecords.logic.jpa.mapper

import org.goafabric.core.medicalrecords.controller.dto.Encounter
import org.goafabric.core.medicalrecords.persistence.jpa.entity.EncounterEo
import org.mapstruct.Mapper
import org.mapstruct.ReportingPolicy

@Mapper(componentModel = "cdi", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = [MedicalRecordMapper::class])
interface EncounterMapper {
    fun map(value: EncounterEo): Encounter
    fun map(value: Encounter): EncounterEo
    fun map(values: List<EncounterEo>): List<Encounter>
}
