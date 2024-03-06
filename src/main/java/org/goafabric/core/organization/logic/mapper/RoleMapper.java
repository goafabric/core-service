package org.goafabric.core.organization.logic.mapper;

import org.goafabric.core.organization.controller.dto.Role;
import org.goafabric.core.organization.persistence.entity.RoleEo;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoleMapper {
    Role map(RoleEo value);

    RoleEo map(Role value);

    List<Role> map(List<RoleEo> value);
}
