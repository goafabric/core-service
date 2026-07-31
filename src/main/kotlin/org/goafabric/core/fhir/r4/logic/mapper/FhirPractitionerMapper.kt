package org.goafabric.core.fhir.r4.logic.mapper

import org.goafabric.core.fhir.r4.controller.dto.HumanName
import org.goafabric.core.fhir.r4.controller.dto.identifier.Coding
import org.goafabric.core.fhir.r4.controller.dto.identifier.Identifier
import org.goafabric.core.fhir.r4.controller.dto.identifier.IdentifierUse
import org.goafabric.core.fhir.r4.controller.dto.identifier.Type
import org.goafabric.core.fhir.r4.controller.dto.Practitioner as FhirPractitioner
import org.goafabric.core.fhir.r4.controller.dto.Telecom as FhirTelecom
import org.goafabric.core.organization.controller.dto.ContactPoint as OrgContactPoint
import org.goafabric.core.organization.controller.dto.Practitioner as OrgPractitioner
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.ReportingPolicy

@Mapper(componentModel = "cdi", unmappedTargetPolicy = ReportingPolicy.IGNORE)
abstract class FhirPractitionerMapper : FhirBaseMapper {

    @Mapping(target = "contactPoint", expression = "java(mapContactPoints(value.getTelecom()))")
    @Mapping(target = "address", expression = "java(mapToOrgAddresses(value.getAddress()))")
    abstract fun map(value: FhirPractitioner): OrgPractitioner

    @Mapping(target = "telecom", expression = "java(mapTelecoms(value.getContactPoint()))")
    @Mapping(target = "name", expression = "java(mapHumanName(value))")
    @Mapping(target = "identifier", expression = "java(mapLanr(value))")
    @Mapping(target = "address", expression = "java(mapToFhirAddresses(value.getAddress()))")
    abstract fun map(value: OrgPractitioner): FhirPractitioner

    @Mapping(target = "telecom", expression = "java(mapTelecoms(value.getContactPoint()))")
    @Mapping(target = "address", expression = "java(mapToFhirAddresses(value.getAddress()))")
    abstract fun mapList(values: List<OrgPractitioner>): List<FhirPractitioner>

    fun map(values: List<OrgPractitioner>): List<FhirPractitioner> = mapList(values)

    fun mapHumanName(value: OrgPractitioner): List<HumanName> =
        listOf(HumanName("", value.familyName, listOf(value.givenName ?: "")))

    fun mapLanr(value: OrgPractitioner): List<Identifier> =
        listOf(Identifier(IdentifierUse.official,
            Type(listOf(Coding("LANR", "http://terminology.hl7.org/CodeSystem/v2-0203"))),
            value.lanr, "https://fhir.kbv.de/NamingSystem/KBV_NS_Base_ANR"))

    fun mapTelecoms(contactPoints: List<OrgContactPoint>?): List<FhirTelecom> =
        contactPoints?.map { cp -> FhirTelecom(system = cp.system, value = cp.value, use = cp.use) } ?: emptyList()

    fun mapContactPoints(telecoms: List<FhirTelecom>?): List<OrgContactPoint> =
        telecoms?.map { t -> OrgContactPoint(system = t.system, value = t.value, use = t.use) } ?: emptyList()

    fun mapToFhirAddresses(addresses: List<org.goafabric.core.organization.controller.dto.Address>?): List<org.goafabric.core.fhir.r4.controller.dto.Address> =
        addresses?.map { mapToFhirAddress(it) } ?: emptyList()

    fun mapToOrgAddresses(addresses: List<org.goafabric.core.fhir.r4.controller.dto.Address>?): List<org.goafabric.core.organization.controller.dto.Address> =
        addresses?.map { mapToOrgAddress(it) } ?: emptyList()
}
