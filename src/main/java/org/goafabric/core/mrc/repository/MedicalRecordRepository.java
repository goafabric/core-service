package org.goafabric.core.mrc.repository;


import org.goafabric.core.mrc.repository.entity.MedicalRecordEo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MedicalRecordRepository extends CrudRepository<MedicalRecordEo, String> {
    //@Query(nativeQuery = true, value = "select * from condition WHERE encounter_id = :encounterId and to_tsvector('english', display) @@ phraseto_tsquery('english', concat(:display, ':*'))")
    List<MedicalRecordEo> findByEncounterIdAndDisplayContainsIgnoreCase(String encounterId, String display);

    @Query("select c from MedicalRecordEo c")
    List<MedicalRecordEo> findAllByEncounterId(String encounterId, TextCriteria criteria);

}

