package org.goafabric.core.medicalrecords.repository.elastic.repository;


import org.goafabric.core.medicalrecords.repository.elastic.repository.entity.MedicalRecordElo;
import org.springframework.data.repository.CrudRepository;

public interface MedicalRecordRepositoryElastic extends CrudRepository<MedicalRecordElo, String> {
    MedicalRecordElo findByRelation(String relation);
}
