package org.goafabric.core.medicalrecords.logic.jpa;

import jakarta.transaction.Transactional;
import org.goafabric.core.medicalrecords.controller.dto.MedicalRecord;
import org.goafabric.core.medicalrecords.controller.dto.RecordAble;
import org.goafabric.core.medicalrecords.logic.MedicalRecordLogicAble;
import org.goafabric.core.medicalrecords.logic.RecordDeleteAble;
import org.goafabric.core.medicalrecords.logic.mapper.MedicalRecordMapper;
import org.goafabric.core.medicalrecords.repository.jpa.MedicalRecordRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Transactional
@Profile("jpa")
public class MedicalRecordLogic implements MedicalRecordLogicAble {

    private final MedicalRecordMapper mapper;

    private final MedicalRecordRepository repository;

    private final List<RecordDeleteAble> recordDeleteAbles;

    public MedicalRecordLogic(MedicalRecordMapper mapper, MedicalRecordRepository repository, @Lazy List<RecordDeleteAble> recordDeleteAbles) {
        this.mapper = mapper;
        this.repository = repository;
        this.recordDeleteAbles = recordDeleteAbles;
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
    public MedicalRecord saveRelatedRecord(String relation, RecordAble recordAble) {
        return recordAble.id() != null
                ? updateRelatedRecord(recordAble)
                : save(new MedicalRecord(null, null, null, recordAble.type(), recordAble.toDisplay(), recordAble.code(), relation));
    }

    private MedicalRecord updateRelatedRecord(RecordAble recordAble) {
        var medicalRecord = getByRelation(recordAble.id());
        return save(new MedicalRecord(medicalRecord.id(), medicalRecord.encounterId(), medicalRecord.version(),
                medicalRecord.type(), recordAble.toDisplay(), medicalRecord.code(), medicalRecord.relation()));
    }

    public void delete(String id) {
        var medicalRecord = getById(id);
        repository.deleteById(id);
        deleteRelatedRecords(medicalRecord);
    }

    //brute force deletion of all assosciated records like bodyMetrics, works but can get very slow, might be better to retrieve the specific instance by type
    private void deleteRelatedRecords(MedicalRecord medicalRecord) {
        recordDeleteAbles.forEach(r -> {
            if (medicalRecord.relation() != null) {
                r.delete(medicalRecord.relation());
            }
        });
    }

    private MedicalRecord getByRelation(String relation) {
        return mapper.map(repository.findByRelation(relation));
    }

}
