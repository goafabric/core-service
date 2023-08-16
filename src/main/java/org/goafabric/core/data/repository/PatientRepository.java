package org.goafabric.core.data.repository;

import org.goafabric.core.data.repository.entity.PatientEo;
import org.goafabric.core.data.repository.entity.PatientNamesOnly;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PatientRepository extends CrudRepository<PatientEo, String> {
    List<PatientEo> findByGivenNameStartsWithIgnoreCase(String givenName);

    List<PatientEo> findByFamilyNameStartsWithIgnoreCase(String familyName);

    List<PatientNamesOnly> findPatientNamesByFamilyNameStartsWithIgnoreCaseOrderByFamilyName(String familyName);

}

