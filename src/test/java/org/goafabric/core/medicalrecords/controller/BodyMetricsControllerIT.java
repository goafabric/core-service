package org.goafabric.core.medicalrecords.controller;

import org.goafabric.core.medicalrecords.controller.dto.BodyMetrics;
import org.goafabric.core.medicalrecords.logic.MedicalRecordLogicAble;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class BodyMetricsControllerIT {
    @Autowired
    private BodyMetricsController bodyMetricsController;

    @Autowired
    private MedicalRecordLogicAble medicalRecordLogic;

    @Test
    void getById() {
        var medicalRecord = bodyMetricsController.save(
            new BodyMetrics(null, null, "170 cm", "100 cm", "30 cm", "30 %")
        );

        var bodyMetric = bodyMetricsController.getById(medicalRecord.relation());
        assertThat(bodyMetric).isNotNull();
        assertThat(medicalRecordLogic.getById(medicalRecord.id())).isNotNull();

        //update
        /*
        bodyMetricsController.save(
                new BodyMetrics(bodyMetric.id(), bodyMetric.version(), "changed", "100 cm", "30 cm", "30 %")
        );
        assertThat(medicalRecordLogic.getById(medicalRecord.id()).display()).contains("changed");

         */

    }
}