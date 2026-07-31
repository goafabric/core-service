package org.goafabric.core.medicalrecords.logic.jpa.mapper

import org.goafabric.core.medicalrecords.controller.dto.BodyMetrics
import org.goafabric.core.medicalrecords.persistence.jpa.entity.BodyMetricsEo
import org.mapstruct.Mapper
import org.mapstruct.ReportingPolicy

@Mapper(componentModel = "cdi", unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface BodyMetricsMapper {
    fun map(value: BodyMetricsEo): BodyMetrics
    fun map(value: BodyMetrics): BodyMetricsEo
}
