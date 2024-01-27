package org.goafabric.core.medicalrecords.repository.elastic.repository;


import org.goafabric.core.medicalrecords.repository.elastic.repository.entity.EncounterElo;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EncounterRepositoryElastic extends CrudRepository<EncounterElo, String> {
    List<EncounterElo> findByPatientIdAndOrgunitId(String patientId, String orgunitId);
}

