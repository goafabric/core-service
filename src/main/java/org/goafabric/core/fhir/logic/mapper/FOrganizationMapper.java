package org.goafabric.core.fhir.logic.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;


@Mapper(componentModel = "spring")
public interface FOrganizationMapper extends FhirBaseMapper{
    @Mapping(source = "telecom", target = "contactPoint")
    org.goafabric.core.data.controller.vo.Organization map(org.goafabric.core.fhir.controller.vo.Organization value);

    @Mapping(source = "contactPoint", target = "telecom")
    org.goafabric.core.fhir.controller.vo.Organization map(org.goafabric.core.data.controller.vo.Organization value);

    @Mapping(source = "contactPoint", target = "telecom")
    List<org.goafabric.core.fhir.controller.vo.Organization> map(List<org.goafabric.core.data.controller.vo.Organization> value);
}
