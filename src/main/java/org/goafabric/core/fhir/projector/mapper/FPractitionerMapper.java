package org.goafabric.core.fhir.projector.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;


@Mapper(componentModel = "spring")
public interface FPractitionerMapper extends FhirBaseMapper {
    @Mapping(source = "telecom", target = "contactPoint")
    org.goafabric.core.data.controller.vo.Practitioner map(org.goafabric.core.fhir.projector.vo.Practitioner value);

    @Mapping(source = "contactPoint", target = "telecom")
    org.goafabric.core.fhir.projector.vo.Practitioner map(org.goafabric.core.data.controller.vo.Practitioner value);

    @Mapping(source = "contactPoint", target = "telecom")
    List<org.goafabric.core.fhir.projector.vo.Practitioner> map(List<org.goafabric.core.data.controller.vo.Practitioner> value);

}
