package org.goafabric.core.mrc.repository;

import org.goafabric.core.mrc.repository.entity.MedicalRecordEo;
import org.springframework.data.repository.CrudRepository;

public interface MedicalRecordRepository extends CrudRepository<MedicalRecordEo, String> {
}

