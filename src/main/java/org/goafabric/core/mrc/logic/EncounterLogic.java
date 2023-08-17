package org.goafabric.core.mrc.logic;

import jakarta.transaction.Transactional;
import org.goafabric.core.mrc.controller.vo.Encounter;
import org.goafabric.core.mrc.logic.mapper.EncounterMapper;
import org.goafabric.core.mrc.repository.EncounterRepository;
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

    public void save(Encounter encounter) {
        repository.save(mapper.map(encounter));
    }

    public List<Encounter> findByPatientIdAndDisplay(String patientId, String text) {
        return mapper.map(
                repository.findByPatientIdAndMedicalRecords_DisplayContainsIgnoreCase(patientId, text));
    }

}
