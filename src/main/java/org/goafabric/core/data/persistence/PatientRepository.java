package org.goafabric.core.data.persistence;

import org.goafabric.core.data.persistence.domain.PatientBo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PatientRepository extends JpaRepository<PatientBo, String> {
    List<PatientBo> findByGivenName(String givenName);

    List<PatientBo> findByFamilyName(@Param("familyName") String familyName);
}

