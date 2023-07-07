package org.goafabric.core.fhir.logic.mapper;

import org.mapstruct.Mapper;

import java.util.List;


@Mapper(componentModel = "spring")
public interface FPractitionerMapper {
    org.goafabric.core.data.controller.vo.Practitioner map(org.goafabric.core.fhir.controller.vo.Practitioner value);

    org.goafabric.core.fhir.controller.vo.Practitioner map(org.goafabric.core.data.controller.vo.Practitioner value);

    List<org.goafabric.core.fhir.controller.vo.Practitioner> map(List<org.goafabric.core.data.controller.vo.Practitioner> value);

}
