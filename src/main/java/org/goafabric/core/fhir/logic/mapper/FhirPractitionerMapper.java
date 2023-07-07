package org.goafabric.core.fhir.logic.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;


@Mapper(componentModel = "spring")
public interface FhirPractitionerMapper extends FhirBaseMapper {
    @Mapping(source = "telecom", target = "contactPoint")
    org.goafabric.core.data.controller.vo.Practitioner map(org.goafabric.core.fhir.controller.vo.Practitioner value);

    @Mapping(source = "contactPoint", target = "telecom")
    org.goafabric.core.fhir.controller.vo.Practitioner map(org.goafabric.core.data.controller.vo.Practitioner value);

    @Mapping(source = "contactPoint", target = "telecom")
    List<org.goafabric.core.fhir.controller.vo.Practitioner> map(List<org.goafabric.core.data.controller.vo.Practitioner> value);

}
