package org.goafabric.core.medicalrecords.logic.jpa;

import jakarta.transaction.Transactional;
import org.goafabric.core.medicalrecords.controller.dto.Encounter;
import org.goafabric.core.medicalrecords.logic.EncounterLogicAble;
import org.goafabric.core.medicalrecords.logic.mapper.EncounterMapper;
import org.goafabric.core.medicalrecords.repository.jpa.EncounterRepository;
import org.h2.util.StringUtils;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Transactional
@Profile("jpa")
public class EncounterLogic implements EncounterLogicAble {

    private final EncounterMapper mapper;

    private final EncounterRepository repository;

    public EncounterLogic(EncounterMapper encounterMapper, EncounterRepository encounterRepository) {
        this.mapper = encounterMapper;
        this.repository = encounterRepository;;
    }

    public Encounter save(Encounter encounter) {
        return mapper.map(repository.save(mapper.map(encounter)));
    }

    public List<Encounter> findByPatientIdAndDisplay(String patientId, String text) {
        return StringUtils.isNullOrEmpty(text)
                ? mapper.map(repository.findByPatientId(patientId))
                : mapper.map(repository.findByPatientIdAndMedicalRecords_DisplayContainsIgnoreCase(patientId, text));
    }

}
