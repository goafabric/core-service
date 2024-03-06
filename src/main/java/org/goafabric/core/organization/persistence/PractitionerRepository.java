package org.goafabric.core.organization.persistence;

import org.goafabric.core.organization.persistence.entity.PractitionerEo;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PractitionerRepository extends CrudRepository<PractitionerEo, String> {
    List<PractitionerEo> findByGivenNameStartsWithIgnoreCase(String givenName);

    List<PractitionerEo> findByFamilyNameStartsWithIgnoreCase(String familyName);
}

