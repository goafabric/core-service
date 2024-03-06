package org.goafabric.core.medicalrecords.persistence.jpa;

import org.goafabric.core.medicalrecords.persistence.jpa.entity.MedicalRecordEo;
import org.springframework.data.repository.CrudRepository;

public interface MedicalRecordRepository extends CrudRepository<MedicalRecordEo, String> {
    MedicalRecordEo findBySpecialization(String specialization);
}

