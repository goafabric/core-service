package org.goafabric.core.mrc.repository;

import org.goafabric.core.mrc.repository.entity.EncounterEo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EncounterRepository extends CrudRepository<EncounterEo, String> {

    //@Query(nativeQuery = true, value = "select T1.* from encounter T1 inner join medical_record T2 on T1.id = T2.encounter_id WHERE T1.patient_id = :patientId and to_tsvector('english', display) @@ to_tsquery('english', concat(:display, ':*'))")
    @Query("SELECT e FROM EncounterEo e JOIN FETCH e.medicalRecords m WHERE e.patientId = :patientId AND UPPER(m.display) LIKE UPPER(concat('%', :display, '%'))")
    List<EncounterEo> findByPatientIdAndMedicalRecords_DisplayContainsIgnoreCase(String patientId, String display);

    @Query("select e from EncounterEo e")
    List<EncounterEo> findAllByPatientId(String patientId, TextCriteria display);

}

