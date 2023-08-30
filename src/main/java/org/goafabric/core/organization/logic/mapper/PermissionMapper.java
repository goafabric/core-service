package org.goafabric.core.organization.logic.mapper;

import org.goafabric.core.organization.controller.vo.Permission;
import org.goafabric.core.organization.repository.entity.PermissionEo;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PermissionMapper extends WorkaroundMapper {
    Permission map(PermissionEo value);

    PermissionEo map(Permission value);

    List<Permission> map(List<PermissionEo> value);

    List<Permission> map(Iterable<PermissionEo> value);

    List<PermissionEo> maps(List<Permission> value);
}
