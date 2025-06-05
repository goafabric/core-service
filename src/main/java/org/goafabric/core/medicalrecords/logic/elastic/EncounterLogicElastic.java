package org.goafabric.core.medicalrecords.logic.elastic;

import org.goafabric.core.extensions.UserContext;
import org.goafabric.core.medicalrecords.controller.dto.Encounter;
import org.goafabric.core.medicalrecords.controller.dto.MedicalRecord;
import org.goafabric.core.medicalrecords.controller.dto.MedicalRecordType;
import org.goafabric.core.medicalrecords.logic.EncounterLogic;
import org.goafabric.core.medicalrecords.logic.elastic.mapper.EncounterMapperElastic;
import org.goafabric.core.medicalrecords.persistence.elastic.repository.EncounterRepositoryElastic;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional(propagation = Propagation.REQUIRES_NEW)
@Profile("elastic")
public class EncounterLogicElastic implements EncounterLogic {

    private final EncounterMapperElastic mapper;

    private final EncounterRepositoryElastic encounterRepository;

    private final MedicalRecordLogicElastic medicalRecordLogic;

    public EncounterLogicElastic(EncounterMapperElastic mapper, EncounterRepositoryElastic encounterRepository, MedicalRecordLogicElastic medicalRecordLogic) {
        this.mapper = mapper;
        this.encounterRepository = encounterRepository;
        this.medicalRecordLogic = medicalRecordLogic;
    }

    public Encounter getById(String id) {
        return mapper.map(encounterRepository.findById(id).orElseThrow());
    }

    public List<Encounter> findByPatientIdAndDisplayAndType(String patientId, String text, List<MedicalRecordType> types) {
        var encounters = findByPatientIdAndDisplay(patientId, text);
        return (!types.isEmpty()
                ? encounters.stream().map(e ->
                new Encounter(e.id(), e.version(), e.patientId(), e.practitionerId(), e.encounterDate(), e.encounterName(),
                        e.medicalRecords().stream().filter(medicalRecord -> types.contains(medicalRecord.type())).toList())).toList()
                : encounters);
    }

    //manually load the specializations, this could be optimized by using an "in" operatin with all encounterIds
    public List<Encounter> findByPatientIdAndDisplay(String patientId, String text) {
        return encounterRepository
                .findByPatientIdAndOrganizationId(patientId, UserContext.getOrganizationId())
                .stream()
                .map(encounterEo -> new Encounter(encounterEo.getId(), encounterEo.getVersion(), encounterEo.getPatientId(), encounterEo.getPractitionerId(),
                        encounterEo.getEncounterDate(), encounterEo.getEncounterName(),
                        medicalRecordLogic.findByEncounterIdAndDisplay(encounterEo.getId(), text)))
                .toList();
    }


    //the save operations manually manages specializations, to have the possibility to update medicalrecords on their own, which is not easily possible with "nested"
    public Encounter save(Encounter encounter) {
        var enc = mapper.map(encounterRepository.save(mapper.map(encounter)));
        encounter.medicalRecords().forEach(medicalRecord -> medicalRecordLogic.save(
                new MedicalRecord(medicalRecord.id(), enc.id(), null, medicalRecord.type(), medicalRecord.display(), medicalRecord.code(), medicalRecord.specialization())));
        return enc;
    }

    @Override
    public void delete(String id) {
        encounterRepository.deleteById(id);
    }

    public void deleteAllByPatientId(String patientId) {
        findByPatientIdAndDisplay(patientId, "").forEach(encounter -> {
            encounter.medicalRecords().forEach(medicalRecord -> medicalRecordLogic.delete(medicalRecord.id()));
            delete(encounter.id());
        });
    }

}
