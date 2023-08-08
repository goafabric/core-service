package org.goafabric.core.mrc.repository;

import org.goafabric.core.mrc.repository.entity.EncounterEo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EncounterRepository extends CrudRepository<EncounterEo, String> {
    List<EncounterEo> findByPatientId(String patientId);

    @Query("select e from EncounterEo e")
    List<EncounterEo> findAllByPatientId(String patientId, TextCriteria criteria);
}

