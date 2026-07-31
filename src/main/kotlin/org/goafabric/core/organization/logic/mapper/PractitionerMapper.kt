package org.goafabric.core.organization.logic.mapper

import org.goafabric.core.organization.controller.dto.Practitioner
import org.goafabric.core.organization.persistence.entity.PractitionerEo
import org.mapstruct.Mapper
import org.mapstruct.ReportingPolicy

@Mapper(componentModel = "cdi", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = [AddressMapper::class])
interface PractitionerMapper {
    fun map(value: PractitionerEo): Practitioner
    fun map(value: Practitioner): PractitionerEo
    fun map(values: List<PractitionerEo>): List<Practitioner>
}
