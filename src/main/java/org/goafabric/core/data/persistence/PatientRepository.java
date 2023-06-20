package org.goafabric.core.data.persistence;

import org.goafabric.core.data.persistence.domain.PatientBo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PatientRepository extends JpaRepository<PatientBo, String> {
    List<PatientBo> findByGivenNameStartsWithIgnoreCase(String givenName);

    List<PatientBo> findByFamilyNameStartsWithIgnoreCase(String familyName);
}

