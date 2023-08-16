package org.goafabric.core.mrc.logic;

import jakarta.transaction.Transactional;
import org.goafabric.core.mrc.controller.vo.Encounter;
import org.goafabric.core.mrc.repository.EncounterRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Transactional
public class EncounterLogic {

    private final EncounterMapper encounterMapper;

    private final EncounterRepository encounterRepository;

    public EncounterLogic(EncounterMapper encounterMapper, EncounterRepository encounterRepository) {
        this.encounterMapper = encounterMapper;
        this.encounterRepository = encounterRepository;;
    }

    public void save(Encounter encounter) {
        encounterRepository.save(encounterMapper.map(encounter));
    }

    public List<Encounter> findByPatientIdAndText(String patientId, String text) {
        return encounterMapper.map(
                encounterRepository.findByPatientIdAndMedicalRecords_DisplayContainsIgnoreCase(patientId, text, PageRequest.of(0, 100)));
    }

}
