package org.goafabric.core.medicalrecords.logic.jpa;

import jakarta.transaction.Transactional;
import org.goafabric.core.medicalrecords.controller.dto.Encounter;
import org.goafabric.core.medicalrecords.controller.dto.MedicalRecordType;
import org.goafabric.core.medicalrecords.logic.jpa.mapper.EncounterMapper;
import org.goafabric.core.medicalrecords.persistence.jpa.EncounterRepository;
import org.h2.util.StringUtils;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Transactional
@Profile("jpa")
public class EncounterLogic implements org.goafabric.core.medicalrecords.logic.EncounterLogic {

    private final EncounterMapper mapper;

    private final EncounterRepository repository;

    private final MedicalRecordLogic medicalRecordLogic;

    public EncounterLogic(EncounterMapper encounterMapper, EncounterRepository encounterRepository, MedicalRecordLogic medicalRecordLogic) {
        this.mapper = encounterMapper;
        this.repository = encounterRepository;
        this.medicalRecordLogic = medicalRecordLogic;
        ;
    }

    public Encounter save(Encounter encounter) {
        return mapper.map(repository.save(mapper.map(encounter)));
    }

    //yuck .. thats not nice
    public List<Encounter> findByPatientIdAndDisplayAndType(String patientId, String text, List<MedicalRecordType> types) {
        var encounters = findByPatientIdAndDisplay(patientId, text);
        return (!types.isEmpty()
                ? encounters.stream().map(e ->
                new Encounter(e.id(), e.version(), e.patientId(), e.practitionerId(), e.encounterDate(), e.encounterName(),
                        e.medicalRecords().stream().filter(record -> types.contains(record.type())).toList())).toList()
                : encounters);
    }

    public List<Encounter> findByPatientIdAndDisplay(String patientId, String text) {
        return StringUtils.isNullOrEmpty(text)
                ? mapper.map(repository.findByPatientId(patientId))
                : mapper.map(repository.findByPatientIdAndMedicalRecords_DisplayContainsIgnoreCase(patientId, text));
    }

    @Override
    public void delete(String id) {
        repository.deleteById(id);
    }

    public void deleteAllByPatientId(String patientId) {
        findByPatientIdAndDisplay(patientId, "").forEach(encounter -> {
            encounter.medicalRecords().forEach(medicalRecord -> medicalRecordLogic.delete(medicalRecord.id()));
            delete(encounter.id());
        });
    }

}
