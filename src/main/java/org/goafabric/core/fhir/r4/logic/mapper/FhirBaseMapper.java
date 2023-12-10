package org.goafabric.core.fhir.r4.logic.mapper;

import org.mapstruct.Mapping;

public interface FhirBaseMapper {
    @Mapping(expression = "java(value.getStreet())", target = "street")
    org.goafabric.core.organization.controller.dto.Address map(org.goafabric.core.fhir.r4.controller.dto.Address value);

    @Mapping(expression = "java(java.util.Collections.singletonList(value.street()))", target = "line")
    org.goafabric.core.fhir.r4.controller.dto.Address map(org.goafabric.core.organization.controller.dto.Address value);
}
