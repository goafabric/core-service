package org.goafabric.core.medicalrecords.logic;

import jakarta.transaction.Transactional;
import org.goafabric.core.medicalrecords.controller.vo.Encounter;
import org.goafabric.core.medicalrecords.logic.mapper.EncounterMapper;
import org.goafabric.core.medicalrecords.repository.EncounterRepository;
import org.goafabric.core.medicalrecords.repository.entity.EncounterEo;
import org.h2.util.StringUtils;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Transactional
public class EncounterLogic {

    private final EncounterMapper mapper;

    private final EncounterRepository repository;

    public EncounterLogic(EncounterMapper encounterMapper, EncounterRepository encounterRepository) {
        this.mapper = encounterMapper;
        this.repository = encounterRepository;;
    }

    public EncounterEo save(Encounter encounter) {
        return repository.save(mapper.map(encounter));
    }

    public List<Encounter> findByPatientIdAndDisplay(String patientId, String text) {
        return StringUtils.isNullOrEmpty(text)
                ? mapper.map(repository.findByPatientId(patientId))
                : mapper.map(repository.findByPatientIdAndMedicalRecords_DisplayContainsIgnoreCase(patientId, text));
    }

}
