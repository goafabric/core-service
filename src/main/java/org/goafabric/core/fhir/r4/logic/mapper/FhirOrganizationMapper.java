package org.goafabric.core.fhir.r4.logic.mapper;

import org.goafabric.core.fhir.r4.controller.vo.identifier.Coding;
import org.goafabric.core.fhir.r4.controller.vo.identifier.Identifier;
import org.goafabric.core.fhir.r4.controller.vo.identifier.IdentifierUse;
import org.goafabric.core.fhir.r4.controller.vo.identifier.Type;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.Collections;
import java.util.List;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FhirOrganizationMapper extends FhirBaseMapper{
    @Mapping(source = "telecom", target = "contactPoint")
    org.goafabric.core.organization.controller.dto.Organization map(org.goafabric.core.fhir.r4.controller.vo.Organization value);

    @Mapping(source = "contactPoint", target = "telecom")
    @Mapping(expression = "java(mapLanr(value))", target = "identifier")
    org.goafabric.core.fhir.r4.controller.vo.Organization map(org.goafabric.core.organization.controller.dto.Organization value);

    @Mapping(source = "contactPoint", target = "telecom")
    List<org.goafabric.core.fhir.r4.controller.vo.Organization> map(List<org.goafabric.core.organization.controller.dto.Organization> value);

    default List<Identifier> mapLanr(org.goafabric.core.organization.controller.dto.Organization value) {
        return Collections.singletonList(new Identifier(IdentifierUse.official,
                new Type(Collections.singletonList(new Coding("BSNR", "http://terminology.hl7.org/CodeSystem/v2-0203"))),
                value.bsnr(), "https://fhir.kbv.de/NamingSystem/KBV_NS_Base_BSNR"));
    }

}
