package org.goafabric.core.medicalrecords.repository;

import org.goafabric.core.medicalrecords.repository.entity.EncounterEo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EncounterRepository extends CrudRepository<EncounterEo, String> {

    @Query("SELECT e FROM EncounterEo e JOIN FETCH e.medicalRecords m WHERE e.patientId = :patientId AND UPPER(m.display) LIKE UPPER(concat('%', :display, '%'))")
    List<EncounterEo> findByPatientIdAndMedicalRecords_DisplayContainsIgnoreCase(String patientId, String display);

    @Query("SELECT e FROM EncounterEo e JOIN FETCH e.medicalRecords m WHERE e.patientId = :patientId")
    List<EncounterEo> findByPatientId(String patientId);

    //@Query("select e from EncounterEo e")
    //List<EncounterEo> findAllByPatientId(String patientId, TextCriteria display);

}

