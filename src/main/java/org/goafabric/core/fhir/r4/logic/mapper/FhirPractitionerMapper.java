package org.goafabric.core.fhir.r4.logic.mapper;

import org.goafabric.core.fhir.r4.controller.dto.HumanName;
import org.goafabric.core.fhir.r4.controller.dto.identifier.Coding;
import org.goafabric.core.fhir.r4.controller.dto.identifier.Identifier;
import org.goafabric.core.fhir.r4.controller.dto.identifier.IdentifierUse;
import org.goafabric.core.fhir.r4.controller.dto.identifier.Type;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.Collections;
import java.util.List;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FhirPractitionerMapper extends FhirBaseMapper {
    @Mapping(source = "telecom", target = "contactPoint")
    org.goafabric.core.organization.controller.dto.Practitioner map(org.goafabric.core.fhir.r4.controller.dto.Practitioner value);

    @Mapping(source = "contactPoint", target = "telecom")
    @Mapping(expression = "java(mapHumanName(value))", target = "name")
    @Mapping(expression = "java(mapLanr(value))", target = "identifier")
    org.goafabric.core.fhir.r4.controller.dto.Practitioner map(org.goafabric.core.organization.controller.dto.Practitioner value);

    @Mapping(source = "contactPoint", target = "telecom")
    List<org.goafabric.core.fhir.r4.controller.dto.Practitioner> map(List<org.goafabric.core.organization.controller.dto.Practitioner> value);

    default List<HumanName> mapHumanName(org.goafabric.core.organization.controller.dto.Practitioner value) {
        return Collections.singletonList(new HumanName("", value.familyName(), Collections.singletonList(value.givenName())));
    }

    default List<Identifier> mapLanr(org.goafabric.core.organization.controller.dto.Practitioner value) {
        return Collections.singletonList(new Identifier(IdentifierUse.official,
                new Type(Collections.singletonList(new Coding("LANR", "http://terminology.hl7.org/CodeSystem/v2-0203"))),
                value.lanr(), "https://fhir.kbv.de/NamingSystem/KBV_NS_Base_ANR"));
    }
}
