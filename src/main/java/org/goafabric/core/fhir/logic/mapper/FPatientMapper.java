package org.goafabric.core.fhir.logic.mapper;

import org.mapstruct.Mapper;

import java.util.List;


@Mapper(componentModel = "spring")
public interface FPatientMapper {
    org.goafabric.core.data.controller.vo.Patient map(org.goafabric.core.fhir.controller.vo.Patient value);

    org.goafabric.core.fhir.controller.vo.Patient map(org.goafabric.core.data.controller.vo.Patient value);

    List<org.goafabric.core.fhir.controller.vo.Patient> map(List<org.goafabric.core.data.controller.vo.Patient> value);

}
