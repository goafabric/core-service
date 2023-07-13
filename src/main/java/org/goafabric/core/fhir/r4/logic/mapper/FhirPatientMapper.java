package org.goafabric.core.fhir.r4.logic.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FhirPatientMapper extends FhirBaseMapper {
    @Mapping(source = "telecom", target = "contactPoint")
    org.goafabric.core.data.controller.vo.Patient map(org.goafabric.core.fhir.r4.controller.vo.Patient value);

    @Mapping(source = "contactPoint", target = "telecom")
    @Mapping(expression = "java(Collections.singletonList(value.address.street))", target = "address.line")
    org.goafabric.core.fhir.r4.controller.vo.Patient map(org.goafabric.core.data.controller.vo.Patient value);

    @Mapping(source = "contactPoint", target = "telecom")
    List<org.goafabric.core.fhir.r4.controller.vo.Patient> map(List<org.goafabric.core.data.controller.vo.Patient> value);



}
