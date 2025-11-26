package org.goafabric.core.organization.persistence;

import org.goafabric.core.organization.persistence.entity.OrganizationEo;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrganizationRepository extends CrudRepository<OrganizationEo, String> {
    List<OrganizationEo> findByNameStartsWith(String name);
}

