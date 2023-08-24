package org.goafabric.core.organization.repository;

import org.goafabric.core.organization.repository.entity.RoleEo;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RoleRepository extends CrudRepository<RoleEo, String> {
    List<RoleEo> findByNameStartsWithIgnoreCase(String name);
}

