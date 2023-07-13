package org.goafabric.core.fhir.r4.logic.mapper;

import org.goafabric.core.fhir.r4.controller.vo.HumanName;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.Collections;
import java.util.List;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FhirPatientMapper extends FhirBaseMapper {
    @Mapping(source = "telecom", target = "contactPoint")
    org.goafabric.core.data.controller.vo.Patient map(org.goafabric.core.fhir.r4.controller.vo.Patient value);

    @Mapping(source = "contactPoint", target = "telecom")
    @Mapping(expression = "java(mapHumanName(value))", target = "name")
    //@Mapping(expression = "java(Collections.singletonList(value.address.street))", target = "address.line")
    org.goafabric.core.fhir.r4.controller.vo.Patient map(org.goafabric.core.data.controller.vo.Patient value);

    @Mapping(source = "contactPoint", target = "telecom")
    List<org.goafabric.core.fhir.r4.controller.vo.Patient> map(List<org.goafabric.core.data.controller.vo.Patient> value);

    default List<HumanName> mapHumanName(org.goafabric.core.data.controller.vo.Patient value) {
        return Collections.singletonList(new HumanName("", value.familyName(), Collections.singletonList(value.givenName())));
    }

}
