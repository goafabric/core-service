package org.goafabric.core.organization.persistence;

import org.goafabric.core.organization.persistence.entity.PermissionEo;
import org.springframework.data.repository.CrudRepository;

public interface PermissionRepository extends CrudRepository<PermissionEo, String> {
}

