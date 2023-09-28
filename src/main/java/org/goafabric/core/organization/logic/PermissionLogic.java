package org.goafabric.core.organization.logic;

import org.goafabric.core.organization.controller.vo.Permission;
import org.goafabric.core.organization.logic.mapper.PermissionMapper;
import org.goafabric.core.organization.repository.PermissionRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PermissionLogic {
    private final PermissionMapper mapper;

    private final PermissionRepository repository;

    public PermissionLogic(PermissionMapper mapper, PermissionRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    public Permission save(Permission permission) {
        return mapper.map(repository.save(
                mapper.map(permission)));
    }

    public List<Permission> findAll() {
        return mapper.map(repository.findAll());
    }

    public List<Permission> saveAll(List<Permission> permission) {
        return mapper.map(repository.saveAll(
                mapper.maps(permission)));
    }
}
