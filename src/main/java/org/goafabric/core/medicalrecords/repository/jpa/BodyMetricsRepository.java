package org.goafabric.core.medicalrecords.repository.jpa;

import org.goafabric.core.medicalrecords.repository.jpa.entity.BodyMetricsEo;
import org.springframework.data.repository.CrudRepository;

public interface BodyMetricsRepository extends CrudRepository<BodyMetricsEo, String> {
}

