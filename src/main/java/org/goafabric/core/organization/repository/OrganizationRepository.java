package org.goafabric.core.organization.repository;

import org.goafabric.core.organization.repository.entity.OrganizationEo;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrganizationRepository extends CrudRepository<OrganizationEo, String> {
    List<OrganizationEo> findByNameStartsWithIgnoreCase(String name);
}

