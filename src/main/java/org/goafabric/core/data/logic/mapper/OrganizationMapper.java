package org.goafabric.core.data.logic.mapper;

import org.goafabric.core.data.controller.vo.Organization;
import org.goafabric.core.data.repository.entity.OrganizationEo;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrganizationMapper extends WorkaroundMapper {
    Organization map(OrganizationEo value);

    OrganizationEo map(Organization value);

    List<Organization> map(List<OrganizationEo> value);

}
