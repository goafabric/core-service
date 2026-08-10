package org.goafabric.core.fhir.r4.logic.mapper

import org.goafabric.core.organization.controller.dto.Address as OrgAddress
import org.goafabric.core.fhir.r4.controller.dto.Address as FhirAddress

interface FhirBaseMapper {

    fun mapToOrgAddress(value: FhirAddress): OrgAddress {
        return OrgAddress(
            id = value.id,
            street = value.getStreet(),
            city = value.city,
            postalCode = value.postalCode,
            state = value.state,
            country = value.country
        )
    }

    fun mapToFhirAddress(value: OrgAddress): FhirAddress {
        return FhirAddress(
            id = value.id,
            line = if (value.street != null) listOf(value.street!!) else emptyList(),
            city = value.city,
            postalCode = value.postalCode,
            state = value.state,
            country = value.country
        )
    }
}
