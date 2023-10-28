package org.goafabric.core.medicalrecords.controller;

import org.goafabric.core.medicalrecords.controller.dto.BodyMetrics;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class BodyMetricsControllerIT {
    @Autowired
    private BodyMetricsController bodyMetricsController;

    @Test
    void getById() {
        var bodyMetrics = bodyMetricsController.save(
            new BodyMetrics(null, null, "170 cm", "100 cm", "30 cm", "30 %")
        );

        assertThat(bodyMetricsController.getById(bodyMetrics.id())).isNotNull();
    }
}