package org.goafabric.core.organization.logic.mapper

import org.goafabric.core.organization.controller.dto.Address
import org.goafabric.core.organization.controller.dto.ContactPoint
import org.goafabric.core.organization.persistence.entity.AddressEo
import org.goafabric.core.organization.persistence.entity.ContactPointEo
import org.mapstruct.Mapper
import org.mapstruct.ReportingPolicy

@Mapper(componentModel = "cdi", unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface AddressMapper {
    fun map(value: AddressEo): Address
    fun map(value: Address): AddressEo
    fun map(value: ContactPointEo): ContactPoint
    fun map(value: ContactPoint): ContactPointEo
}
