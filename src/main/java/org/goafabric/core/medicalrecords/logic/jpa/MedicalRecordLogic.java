package org.goafabric.core.medicalrecords.logic.jpa;

import jakarta.transaction.Transactional;
import org.goafabric.core.medicalrecords.controller.dto.MedicalRecord;
import org.goafabric.core.medicalrecords.controller.dto.MedicalRecordAble;
import org.goafabric.core.medicalrecords.logic.MedicalRecordDeleteAble;
import org.goafabric.core.medicalrecords.logic.jpa.mapper.MedicalRecordMapper;
import org.goafabric.core.medicalrecords.repository.jpa.MedicalRecordRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
@Transactional
@Profile("jpa")
public class MedicalRecordLogic implements org.goafabric.core.medicalrecords.logic.MedicalRecordLogic {

    private final MedicalRecordMapper mapper;

    private final MedicalRecordRepository repository;

    private final List<MedicalRecordDeleteAble> medicalRecordDeleteAbles;

    public MedicalRecordLogic(MedicalRecordMapper mapper, MedicalRecordRepository repository, @Lazy List<MedicalRecordDeleteAble> medicalRecordDeleteAbles) {
        this.mapper = mapper;
        this.repository = repository;
        this.medicalRecordDeleteAbles = Collections.unmodifiableList(medicalRecordDeleteAbles);
    }

    public MedicalRecord getById(String id) {
        return mapper.map(repository.findById(id).get());
    }

    public MedicalRecord save(MedicalRecord medicalRecord) {
        return mapper.map(
            repository.save(mapper.map(medicalRecord))
        );
    }

    //save related records, has to be called by related class like bodymetrics
    public MedicalRecord saveRelatedRecord(String relation, MedicalRecordAble medicalRecordAble) {
        return medicalRecordAble.id() != null
                ? updateRelatedRecord(medicalRecordAble)
                : save(new MedicalRecord(medicalRecordAble.type(), medicalRecordAble.toDisplay(), medicalRecordAble.code(), relation));
    }

    private MedicalRecord updateRelatedRecord(MedicalRecordAble updatedRecord) {
        var medicalRecord = mapper.map(repository.findByRelation(updatedRecord.id()));
        return save(new MedicalRecord(medicalRecord.id(), medicalRecord.encounterId(), medicalRecord.version(), medicalRecord.type(),
                updatedRecord.toDisplay(), updatedRecord.code(), updatedRecord.id()));
    }

    public void delete(String id) {
        deleteRelatedRecords(getById(id));
        repository.deleteById(id);
    }

    //brute force deletion of all assosciated records like bodyMetrics, works but can get very slow, might be better to retrieve the specific instance by type
    private void deleteRelatedRecords(MedicalRecord medicalRecord) {
        Optional.ofNullable(medicalRecord.relation())
                .ifPresent(relation -> medicalRecordDeleteAbles.forEach(record -> record.delete(relation)));
    }

}
