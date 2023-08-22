package org.goafabric.core.fhir.r4.logic.mapper;

import org.goafabric.core.fhir.r4.controller.vo.HumanName;
import org.goafabric.core.organization.controller.vo.Patient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.Collections;
import java.util.List;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FhirPatientMapper extends FhirBaseMapper {
    @Mapping(source = "telecom", target = "contactPoint")
    Patient map(org.goafabric.core.fhir.r4.controller.vo.Patient value);

    @Mapping(source = "contactPoint", target = "telecom")
    @Mapping(expression = "java(mapHumanName(value))", target = "name")
    //@Mapping(expression = "java(Collections.singletonList(value.address.street))", target = "address.line")
    org.goafabric.core.fhir.r4.controller.vo.Patient map(Patient value);

    @Mapping(source = "contactPoint", target = "telecom")
    List<org.goafabric.core.fhir.r4.controller.vo.Patient> map(List<Patient> value);

    default List<HumanName> mapHumanName(Patient value) {
        return Collections.singletonList(new HumanName("", value.familyName(), Collections.singletonList(value.givenName())));
    }

}
