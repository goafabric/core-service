package org.goafabric.core.fhir.logic.mapper;

import org.mapstruct.Mapping;

public interface FhirBaseMapper {
    @Mapping(expression = "java(value.getStreet())", target = "street")
    org.goafabric.core.data.controller.vo.Address map(org.goafabric.core.fhir.controller.vo.Address value);

    @Mapping(expression = "java(java.util.Collections.singletonList(value.street()))", target = "line")
    org.goafabric.core.fhir.controller.vo.Address map(org.goafabric.core.data.controller.vo.Address value);
}
