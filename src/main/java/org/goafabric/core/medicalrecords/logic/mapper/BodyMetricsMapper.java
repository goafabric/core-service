package org.goafabric.core.medicalrecords.logic.mapper;

import org.goafabric.core.medicalrecords.controller.dto.BodyMetrics;
import org.goafabric.core.medicalrecords.repository.entity.BodyMetricsEo;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BodyMetricsMapper {
    BodyMetrics map(BodyMetricsEo value);

    BodyMetricsEo map(BodyMetrics value);

    List<BodyMetrics> map(List<BodyMetricsEo> value);

    List<BodyMetrics> map(Iterable<BodyMetricsEo> value);
}
