package org.goafabric.core.organization.repository;

import org.goafabric.core.organization.repository.entity.PatientEo;
import org.goafabric.core.organization.repository.entity.PatientNamesOnly;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PatientRepository extends CrudRepository<PatientEo, String> {
    //@EntityGraph(attributePaths = {"address", "contactPoint"})
    List<PatientEo> findByGivenNameStartsWithIgnoreCase(String givenName);

    //@EntityGraph(attributePaths = {"address", "contactPoint"}) //force join instead of multiple sql
    List<PatientEo> findByFamilyNameStartsWithIgnoreCase(String familyName);

    List<PatientNamesOnly> findPatientNamesByFamilyNameStartsWithIgnoreCaseOrderByFamilyName(String familyName);

    @Query("SELECT pe1_0 FROM PatientEo pe1_0 " +
            "WHERE (UPPER(pe1_0.familyName) LIKE UPPER(CONCAT('%', :familyName, '%')) OR pe1_0.familySoundex = :familySoundex) " +
            "AND (UPPER(pe1_0.givenName) LIKE UPPER(CONCAT('%', :givenName, '%')) OR pe1_0.givenSoundex = :givenSoundex)")
    List<PatientNamesOnly> findByFamilyNameAndGivenName(String familyName, String familySoundex, String givenName, String givenSoundex);

}

