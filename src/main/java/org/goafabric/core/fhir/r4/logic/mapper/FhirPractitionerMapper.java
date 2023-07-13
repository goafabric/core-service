package org.goafabric.core.fhir.r4.logic.mapper;

import org.goafabric.core.fhir.r4.controller.vo.HumanName;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.Collections;
import java.util.List;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FhirPractitionerMapper extends FhirBaseMapper {
    @Mapping(source = "telecom", target = "contactPoint")
    org.goafabric.core.data.controller.vo.Practitioner map(org.goafabric.core.fhir.r4.controller.vo.Practitioner value);

    @Mapping(source = "contactPoint", target = "telecom")
    @Mapping(expression = "java(mapHumanName(value))", target = "name")
    org.goafabric.core.fhir.r4.controller.vo.Practitioner map(org.goafabric.core.data.controller.vo.Practitioner value);

    @Mapping(source = "contactPoint", target = "telecom")
    List<org.goafabric.core.fhir.r4.controller.vo.Practitioner> map(List<org.goafabric.core.data.controller.vo.Practitioner> value);

    default List<HumanName> mapHumanName(org.goafabric.core.data.controller.vo.Practitioner value) {
        return Collections.singletonList(new HumanName("", value.familyName(), Collections.singletonList(value.givenName())));
    }
}
