package org.goafabric.core.mrc.repository;


import org.goafabric.core.mrc.repository.entity.MedicalRecordEo;
import org.springframework.data.repository.CrudRepository;

public interface MedicalRecordRepository extends CrudRepository<MedicalRecordEo, String> {
    //@Query(nativeQuery = true, value = "select * from medical_record WHERE encounter_id = :encounterId and to_tsvector('english', display) @@ to_tsquery('english', concat(:display, ':*'))")
    //List<MedicalRecordEo> findByEncounterIdAndDisplayContainsIgnoreCase(String encounterId, String display);

    //List<MedicalRecordEo> findByEncounterId(String encounterId);
}

