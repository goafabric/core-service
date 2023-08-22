package org.goafabric.core.medicalrecords.repository;

import org.goafabric.core.medicalrecords.repository.entity.MedicalRecordEo;
import org.springframework.data.repository.CrudRepository;

public interface MedicalRecordRepository extends CrudRepository<MedicalRecordEo, String> {
}

