package org.goafabric.core.fhir.r4.logic.mapper

import org.goafabric.core.fhir.r4.controller.dto.HumanName
import org.goafabric.core.fhir.r4.controller.dto.Patient as FhirPatient
import org.goafabric.core.fhir.r4.controller.dto.Telecom as FhirTelecom
import org.goafabric.core.organization.controller.dto.ContactPoint as OrgContactPoint
import org.goafabric.core.organization.controller.dto.Patient as OrgPatient
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.ReportingPolicy

@Mapper(componentModel = "cdi", unmappedTargetPolicy = ReportingPolicy.IGNORE)
abstract class FhirPatientMapper : FhirBaseMapper {

    @Mapping(target = "contactPoint", expression = "java(mapContactPoints(value.getTelecom()))")
    @Mapping(target = "address", expression = "java(mapToOrgAddresses(value.getAddress()))")
    abstract fun map(value: FhirPatient): OrgPatient

    @Mapping(target = "telecom", expression = "java(mapTelecoms(value.getContactPoint()))")
    @Mapping(target = "name", expression = "java(mapHumanName(value))")
    @Mapping(target = "address", expression = "java(mapToFhirAddresses(value.getAddress()))")
    abstract fun map(value: OrgPatient): FhirPatient

    @Mapping(target = "telecom", expression = "java(mapTelecoms(value.getContactPoint()))")
    @Mapping(target = "address", expression = "java(mapToFhirAddresses(value.getAddress()))")
    abstract fun mapList(values: List<OrgPatient>): List<FhirPatient>

    fun map(values: List<OrgPatient>): List<FhirPatient> = mapList(values)

    fun mapHumanName(value: OrgPatient): List<HumanName> =
        listOf(HumanName("", value.familyName, listOf(value.givenName ?: "")))

    fun mapTelecoms(contactPoints: List<OrgContactPoint>?): List<FhirTelecom> =
        contactPoints?.map { cp -> FhirTelecom(system = cp.system, value = cp.value, use = cp.use) } ?: emptyList()

    fun mapContactPoints(telecoms: List<FhirTelecom>?): List<OrgContactPoint> =
        telecoms?.map { t -> OrgContactPoint(system = t.system, value = t.value, use = t.use) } ?: emptyList()

    fun mapToFhirAddresses(addresses: List<org.goafabric.core.organization.controller.dto.Address>?): List<org.goafabric.core.fhir.r4.controller.dto.Address> =
        addresses?.map { mapToFhirAddress(it) } ?: emptyList()

    fun mapToOrgAddresses(addresses: List<org.goafabric.core.fhir.r4.controller.dto.Address>?): List<org.goafabric.core.organization.controller.dto.Address> =
        addresses?.map { mapToOrgAddress(it) } ?: emptyList()
}
