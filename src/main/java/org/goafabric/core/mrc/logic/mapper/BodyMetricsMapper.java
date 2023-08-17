package org.goafabric.core.mrc.logic.mapper;

import org.goafabric.core.mrc.controller.vo.BodyMetrics;
import org.goafabric.core.mrc.repository.entity.BodyMetricsEo;
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
