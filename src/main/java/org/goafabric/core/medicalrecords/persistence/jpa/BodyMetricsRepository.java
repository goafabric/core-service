package org.goafabric.core.medicalrecords.persistence.jpa;

import org.goafabric.core.medicalrecords.persistence.jpa.entity.BodyMetricsEo;
import org.springframework.data.repository.CrudRepository;

public interface BodyMetricsRepository extends CrudRepository<BodyMetricsEo, String> {
}

