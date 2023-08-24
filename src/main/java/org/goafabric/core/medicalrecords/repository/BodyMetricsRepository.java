package org.goafabric.core.medicalrecords.repository;

import org.goafabric.core.medicalrecords.repository.entity.BodyMetricsEo;
import org.springframework.data.repository.CrudRepository;

public interface BodyMetricsRepository extends CrudRepository<BodyMetricsEo, String> {
}

