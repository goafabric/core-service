package org.goafabric.core.medicalrecords.logic;

import jakarta.transaction.Transactional;
import org.goafabric.core.medicalrecords.controller.vo.BodyMetrics;
import org.goafabric.core.medicalrecords.logic.mapper.BodyMetricsMapper;
import org.goafabric.core.medicalrecords.repository.BodyMetricsRepository;
import org.springframework.stereotype.Component;

@Component
@Transactional
public class BodyMetricsLogic {

    private final BodyMetricsMapper mapper;

    private final BodyMetricsRepository repository;

    public BodyMetricsLogic(BodyMetricsMapper mapper, BodyMetricsRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    public BodyMetrics getById(String id) {
        return mapper.map(repository.findById(id).get());
    }

    public BodyMetrics save(BodyMetrics bodyMetrics) {
        return mapper.map(
                repository.save(mapper.map(bodyMetrics))
        );
    }
}
