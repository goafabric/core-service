package org.goafabric.core.organization.logic.mapper

import org.goafabric.core.organization.controller.dto.Patient
import org.goafabric.core.organization.persistence.entity.PatientEo
import org.mapstruct.Mapper
import org.mapstruct.ReportingPolicy

@Mapper(componentModel = "cdi", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = [AddressMapper::class])
interface PatientMapper {
    fun map(value: PatientEo): Patient
    fun map(value: Patient): PatientEo
    fun map(values: List<PatientEo>): List<Patient>
}
