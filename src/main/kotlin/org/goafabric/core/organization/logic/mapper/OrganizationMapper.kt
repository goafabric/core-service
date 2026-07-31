package org.goafabric.core.organization.logic.mapper

import org.goafabric.core.organization.controller.dto.Organization
import org.goafabric.core.organization.persistence.entity.OrganizationEo
import org.mapstruct.Mapper
import org.mapstruct.ReportingPolicy

@Mapper(componentModel = "cdi", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = [AddressMapper::class])
interface OrganizationMapper {
    fun map(value: OrganizationEo): Organization
    fun map(value: Organization): OrganizationEo
    fun map(values: List<OrganizationEo>): List<Organization>
}
