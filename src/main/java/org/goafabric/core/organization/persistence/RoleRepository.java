package org.goafabric.core.organization.persistence;

import org.goafabric.core.organization.persistence.entity.RoleEo;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RoleRepository extends CrudRepository<RoleEo, String> {
    List<RoleEo> findByNameStartsWith(String name);
}

