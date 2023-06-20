package org.goafabric.core.data.persistence;

import org.goafabric.core.data.persistence.domain.PractitionerBo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PractitionerRepository extends JpaRepository<PractitionerBo, String> {
    List<PractitionerBo> findByGivenNameStartsWithIgnoreCase(String givenName);

    List<PractitionerBo> findByFamilyNameStartsWithIgnoreCase(String familyName);
}

