package org.goafabric.core.fhir.r4.logic.mapper;

import org.goafabric.core.organization.controller.vo.Address;
import org.mapstruct.Mapping;

public interface FhirBaseMapper {
    @Mapping(expression = "java(value.getStreet())", target = "street")
    Address map(org.goafabric.core.fhir.r4.controller.vo.Address value);

    @Mapping(expression = "java(java.util.Collections.singletonList(value.street()))", target = "line")
    org.goafabric.core.fhir.r4.controller.vo.Address map(Address value);
}
