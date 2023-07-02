package org.goafabric.core.data.repository;

import org.goafabric.core.data.repository.entity.OrganizationEo;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrganizationRepository extends CrudRepository<OrganizationEo, String> {
    List<OrganizationEo> findByNameStartsWithIgnoreCase(String name);
}

