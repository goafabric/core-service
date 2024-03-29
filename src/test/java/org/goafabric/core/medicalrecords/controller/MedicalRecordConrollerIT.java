package org.goafabric.core.medicalrecords.controller;

import org.goafabric.core.medicalrecords.controller.dto.MedicalRecord;
import org.goafabric.core.medicalrecords.controller.dto.MedicalRecordType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MedicalRecordConrollerIT {
    @Autowired
    private MedicalRecordController medicalRecordController;

    @Test
    void getById() {
        var medicalRecord = medicalRecordController.save(
                new MedicalRecord(MedicalRecordType.CONDITION, "Adipositas", "E66.00")
        );

        assertThat(medicalRecordController.getById(medicalRecord.id())).isNotNull();

        assertThat(medicalRecordController.save(
            new MedicalRecord(medicalRecord.id(), medicalRecord.encounterId(), medicalRecord.version(), medicalRecord.type(), medicalRecord.display(), medicalRecord.code(), medicalRecord.specialization())
        ).id()).isEqualTo(medicalRecord.id());
    }
}