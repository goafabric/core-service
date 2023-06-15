package org.goafabric.core.data.persistence;

import org.goafabric.core.data.persistence.domain.PatientBo;
import org.goafabric.core.data.persistence.domain.PractitionerBo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PractitionerRepository extends JpaRepository<PractitionerBo, String> {
    List<PatientBo> findByGivenName(String givenName);

    List<PatientBo> findByFamilyName(@Param("familyName") String familyName);
}

