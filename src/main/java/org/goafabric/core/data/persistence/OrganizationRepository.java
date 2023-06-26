package org.goafabric.core.data.persistence;

import org.goafabric.core.data.persistence.domain.OrganizationBo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrganizationRepository extends JpaRepository<OrganizationBo, String> {
    List<OrganizationBo> findByNameStartsWithIgnoreCase(String name);
}

