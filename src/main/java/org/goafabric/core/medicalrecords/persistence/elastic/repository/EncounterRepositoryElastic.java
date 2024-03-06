package org.goafabric.core.medicalrecords.persistence.elastic.repository;


import org.goafabric.core.medicalrecords.persistence.elastic.repository.entity.EncounterElo;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EncounterRepositoryElastic extends CrudRepository<EncounterElo, String> {
    List<EncounterElo> findByPatientIdAndOrganizationId(String patientId, String organizationId);
}

