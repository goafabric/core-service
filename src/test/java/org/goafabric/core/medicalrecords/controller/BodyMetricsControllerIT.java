package org.goafabric.core.medicalrecords.controller;

import org.goafabric.core.medicalrecords.controller.dto.BodyMetrics;
import org.goafabric.core.medicalrecords.logic.MedicalRecordLogicAble;
import org.goafabric.core.medicalrecords.logic.jpa.BodyMetricsLogic;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.NoSuchElementException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@SpringBootTest
class BodyMetricsControllerIT {
    @Autowired
    private BodyMetricsController bodyMetricsController;

    @Autowired
    private MedicalRecordLogicAble medicalRecordLogic;


    @Autowired
    private BodyMetricsLogic bodyMetricsLogic;

    @Test
    void getById() {
        var medicalRecord = bodyMetricsController.save(
            new BodyMetrics(null, null, "170 cm", "100 cm", "30 cm", "30 %")
        );

        var bodyMetric = bodyMetricsController.getById(medicalRecord.relation());
        assertThat(bodyMetric).isNotNull();
        assertThat(medicalRecordLogic.getById(medicalRecord.id())).isNotNull();

        //update
        bodyMetricsController.save(
                new BodyMetrics(bodyMetric.id(), bodyMetric.version(), "changed", "100 cm", "30 cm", "30 %")
        );
        assertThat(medicalRecordLogic.getById(medicalRecord.id()).display()).contains("changed");

    }

    @Test
    public void delete() {
        var medicalRecord = bodyMetricsLogic.save(
                new BodyMetrics(null, null, "170 cm", "100 cm", "30 cm", "30 %")
        );

        var bodyMetric = bodyMetricsLogic.getById(medicalRecord.relation());
        assertThat(bodyMetric).isNotNull();
        assertThat(medicalRecordLogic.getById(medicalRecord.id())).isNotNull();

        bodyMetricsLogic.delete(bodyMetric);

        assertThatThrownBy(() -> bodyMetricsLogic.getById(bodyMetric.id())).isInstanceOf(NoSuchElementException.class);
        assertThatThrownBy(() -> medicalRecordLogic.getById(medicalRecord.id())).isInstanceOf(NoSuchElementException.class);
    }
}