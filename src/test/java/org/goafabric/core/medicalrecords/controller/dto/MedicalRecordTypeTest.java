package org.goafabric.core.medicalrecords.controller.dto;

import org.goafabric.core.medicalrecords.logic.jpa.BodyMetricsLogic;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class MedicalRecordTypeTest {

    @Test
    public void getClassByType() {
        assertThat(MedicalRecordType.getClassByType(MedicalRecordType.BODY_METRICS))
                .isEqualTo(BodyMetricsLogic.class);

        assertThatThrownBy(() -> MedicalRecordType.getClassByType(MedicalRecordType.ANAMNESIS))
                .isInstanceOf(IllegalArgumentException.class);

        assertThatThrownBy(() -> MedicalRecordType.getClassByType(null))
                .isInstanceOf(IllegalArgumentException.class);

    }

}

