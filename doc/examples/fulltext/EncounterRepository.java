package org.goafabric.core.medicalrecords.persistence;

import org.goafabric.core.medicalrecords.persistence.entity.EncounterEo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EncounterRepository extends CrudRepository<EncounterEo, String> {

    @Query("SELECT e FROM EncounterEo e JOIN FETCH e.medicalRecords m WHERE e.patientId = :patientId AND fts(:display)")
    List<EncounterEo> findByPatientIdAndMedicalRecords_DisplayContainsIgnoreCase(String patientId, String display);

    @Query("SELECT e FROM EncounterEo e JOIN FETCH e.medicalRecords m WHERE e.patientId = :patientId")
    List<EncounterEo> findByPatientId(String patientId);

    @Query("select e from EncounterEo e")
    List<EncounterEo> findAllByPatientId(String patientId, TextCriteria display);

}

