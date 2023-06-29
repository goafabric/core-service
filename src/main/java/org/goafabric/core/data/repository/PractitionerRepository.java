package org.goafabric.core.data.repository;

import org.goafabric.core.data.repository.entity.PractitionerEo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PractitionerRepository extends JpaRepository<PractitionerEo, String> {
    List<PractitionerEo> findByGivenNameStartsWithIgnoreCase(String givenName);

    List<PractitionerEo> findByFamilyNameStartsWithIgnoreCase(String familyName);
}

