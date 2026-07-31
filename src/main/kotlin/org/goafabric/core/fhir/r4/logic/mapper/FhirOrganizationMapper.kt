package org.goafabric.core.fhir.r4.logic.mapper

import org.goafabric.core.fhir.r4.controller.dto.identifier.Coding
import org.goafabric.core.fhir.r4.controller.dto.identifier.Identifier
import org.goafabric.core.fhir.r4.controller.dto.identifier.IdentifierUse
import org.goafabric.core.fhir.r4.controller.dto.identifier.Type
import org.goafabric.core.fhir.r4.controller.dto.Organization as FhirOrganization
import org.goafabric.core.fhir.r4.controller.dto.Telecom as FhirTelecom
import org.goafabric.core.organization.controller.dto.ContactPoint as OrgContactPoint
import org.goafabric.core.organization.controller.dto.Organization as OrgOrganization
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.ReportingPolicy

@Mapper(componentModel = "cdi", unmappedTargetPolicy = ReportingPolicy.IGNORE)
abstract class FhirOrganizationMapper : FhirBaseMapper {

    @Mapping(target = "contactPoint", expression = "java(mapContactPoints(value.getTelecom()))")
    @Mapping(target = "address", expression = "java(mapToOrgAddresses(value.getAddress()))")
    abstract fun map(value: FhirOrganization): OrgOrganization

    @Mapping(target = "telecom", expression = "java(mapTelecoms(value.getContactPoint()))")
    @Mapping(target = "identifier", expression = "java(mapBsnr(value))")
    @Mapping(target = "address", expression = "java(mapToFhirAddresses(value.getAddress()))")
    abstract fun map(value: OrgOrganization): FhirOrganization

    @Mapping(target = "telecom", expression = "java(mapTelecoms(value.getContactPoint()))")
    @Mapping(target = "address", expression = "java(mapToFhirAddresses(value.getAddress()))")
    abstract fun mapList(values: List<OrgOrganization>): List<FhirOrganization>

    fun map(values: List<OrgOrganization>): List<FhirOrganization> = mapList(values)

    fun mapBsnr(value: OrgOrganization): List<Identifier> =
        listOf(Identifier(IdentifierUse.official,
            Type(listOf(Coding("BSNR", "http://terminology.hl7.org/CodeSystem/v2-0203"))),
            value.bsnr, "https://fhir.kbv.de/NamingSystem/KBV_NS_Base_BSNR"))

    fun mapTelecoms(contactPoints: List<OrgContactPoint>?): List<FhirTelecom> =
        contactPoints?.map { cp -> FhirTelecom(system = cp.system, value = cp.value, use = cp.use) } ?: emptyList()

    fun mapContactPoints(telecoms: List<FhirTelecom>?): List<OrgContactPoint> =
        telecoms?.map { t -> OrgContactPoint(system = t.system, value = t.value, use = t.use) } ?: emptyList()

    fun mapToFhirAddresses(addresses: List<org.goafabric.core.organization.controller.dto.Address>?): List<org.goafabric.core.fhir.r4.controller.dto.Address> =
        addresses?.map { mapToFhirAddress(it) } ?: emptyList()

    fun mapToOrgAddresses(addresses: List<org.goafabric.core.fhir.r4.controller.dto.Address>?): List<org.goafabric.core.organization.controller.dto.Address> =
        addresses?.map { mapToOrgAddress(it) } ?: emptyList()
}
