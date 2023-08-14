package org.goafabric.core.mrc.repository;

import org.goafabric.core.mrc.repository.entity.EncounterEo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EncounterRepository extends CrudRepository<EncounterEo, String> {

    //@Query(nativeQuery = true, value = "select e1_0.*, m1_0.* from encounter e1_0 left join medical_record m1_0 on e1_0.id=m1_0.encounter_id where e1_0.patient_id= :patientId and upper(m1_0.display) like concat('%', concat(upper(:display), '%'))")
    //@EntityGraph(attributePaths = "medicalRecords")
    List<EncounterEo> findByPatientIdAndMedicalRecords_DisplayContainsIgnoreCase(String patientId, String display);

    @Query("select e from EncounterEo e")
    List<EncounterEo> findAllByPatientId(String patientId, TextCriteria display);

}

