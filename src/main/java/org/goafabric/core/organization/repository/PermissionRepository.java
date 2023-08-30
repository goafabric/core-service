package org.goafabric.core.organization.repository;

import org.goafabric.core.organization.repository.entity.PermissionEo;
import org.springframework.data.repository.CrudRepository;

public interface PermissionRepository extends CrudRepository<PermissionEo, String> {
}

