package org.goafabric.core.medicalrecords.logic.elastic;

import org.goafabric.core.medicalrecords.controller.dto.Encounter;
import org.goafabric.core.medicalrecords.controller.dto.MedicalRecord;
import org.goafabric.core.medicalrecords.logic.EncounterLogic;
import org.goafabric.core.medicalrecords.logic.elastic.mapper.EncounterMapperElastic;
import org.goafabric.core.medicalrecords.repository.elastic.repository.EncounterRepositoryElastic;
import org.goafabric.core.organization.repository.extensions.TenantResolver;
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
        return mapper.map(encounterRepository.findById(id).get());
    }

    //manually load the relations
    public List<Encounter> findByPatientIdAndDisplay(String patientId, String text) {
        return encounterRepository
                .findByPatientIdAndOrgunitId(patientId, TenantResolver.getOrgunitId())
                .stream()
                .map(encounterEo -> new Encounter(encounterEo.getId(), encounterEo.getVersion(), encounterEo.getPatientId(), encounterEo.getPractitionerId(),
                        encounterEo.getEncounterDate(), encounterEo.getEncounterName(),
                        medicalRecordLogic.findByEncounterIdAndDisplay(encounterEo.getId(), text)))
                .toList();
    }

    //the save operations manually manages relations, to have the possibility to update medicalrecords on their own, which is not easily possible with "nested"
    public Encounter save(Encounter encounter) {
        var enc = mapper.map(encounterRepository.save(mapper.map(encounter)));

        encounter.medicalRecords().forEach(medicalRecord -> medicalRecordLogic.save(
                new MedicalRecord(medicalRecord.id(), enc.id(), null, medicalRecord.type(), medicalRecord.display(), medicalRecord.code(), medicalRecord.relation())));
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
