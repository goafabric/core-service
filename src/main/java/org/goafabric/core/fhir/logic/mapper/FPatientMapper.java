package org.goafabric.core.fhir.logic.mapper;

import org.mapstruct.Mapper;

import java.util.List;


@Mapper(componentModel = "spring")
public interface FPatientMapper {
    //@Mapping(source = "gender", target = "gender")
    org.goafabric.core.data.controller.vo.Patient map(org.goafabric.core.fhir.controller.vo.Patient value);

    //@Mapping(source = "contactPoint", target = "telecom")
    org.goafabric.core.fhir.controller.vo.Patient map(org.goafabric.core.data.controller.vo.Patient value);

    //@Mapping(source = "contactPoint", target = "telecom")
    List<org.goafabric.core.fhir.controller.vo.Patient> map(List<org.goafabric.core.data.controller.vo.Patient> value);

}