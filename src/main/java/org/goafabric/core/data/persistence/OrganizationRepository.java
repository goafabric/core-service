package org.goafabric.core.data.persistence;

import org.goafabric.core.data.persistence.domain.OrganizationBo;
import org.goafabric.core.data.persistence.domain.PatientBo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrganizationRepository extends JpaRepository<OrganizationBo, String> {
    List<PatientBo> findByName(String name);
}

