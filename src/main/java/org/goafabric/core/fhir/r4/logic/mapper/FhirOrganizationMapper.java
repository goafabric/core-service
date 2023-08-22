package org.goafabric.core.fhir.r4.logic.mapper;

import org.goafabric.core.organization.controller.vo.Organization;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FhirOrganizationMapper extends FhirBaseMapper{
    @Mapping(source = "telecom", target = "contactPoint")
    Organization map(org.goafabric.core.fhir.r4.controller.vo.Organization value);

    @Mapping(source = "contactPoint", target = "telecom")
    org.goafabric.core.fhir.r4.controller.vo.Organization map(Organization value);

    @Mapping(source = "contactPoint", target = "telecom")
    List<org.goafabric.core.fhir.r4.controller.vo.Organization> map(List<Organization> value);
}
