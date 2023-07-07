package org.goafabric.core.fhir.logic.mapper;

import org.mapstruct.Mapper;

import java.util.List;


@Mapper(componentModel = "spring")
public interface FOrganizationMapper {
    org.goafabric.core.data.controller.vo.Organization map(org.goafabric.core.fhir.controller.vo.Organization value);

    org.goafabric.core.fhir.controller.vo.Organization map(org.goafabric.core.data.controller.vo.Organization value);
    
    List<org.goafabric.core.fhir.controller.vo.Organization> map(List<org.goafabric.core.data.controller.vo.Organization> value);

}
