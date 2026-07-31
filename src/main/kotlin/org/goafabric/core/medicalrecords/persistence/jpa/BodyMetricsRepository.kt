package org.goafabric.core.medicalrecords.persistence.jpa

import io.quarkus.hibernate.panache.PanacheRepository
import org.goafabric.core.medicalrecords.persistence.jpa.entity.BodyMetricsEo

interface BodyMetricsRepository : PanacheRepository.Managed<BodyMetricsEo, String> {

    fun save(bodyMetricsEo: BodyMetricsEo): BodyMetricsEo {
        return session.merge(bodyMetricsEo)
    }
}
