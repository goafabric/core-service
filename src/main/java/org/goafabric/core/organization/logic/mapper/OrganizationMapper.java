package org.goafabric.core.organization.logic.mapper;

import org.goafabric.core.organization.controller.dto.Organization;
import org.goafabric.core.organization.persistence.entity.OrganizationEo;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrganizationMapper {
    Organization map(OrganizationEo value);

    OrganizationEo map(Organization value);

    List<Organization> map(List<OrganizationEo> value);
}
