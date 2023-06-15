package org.goafabric.core.data.logic;

import org.goafabric.core.data.controller.dto.Organization;
import org.goafabric.core.data.persistence.domain.OrganizationBo;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrganizationMapper extends WorkaroundMapper {
    Organization map(OrganizationBo value);

    OrganizationBo map(Organization value);

    List<Organization> map(List<OrganizationBo> value);

}
