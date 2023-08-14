package org.goafabric.core.mrc.logic;

import io.micrometer.common.util.StringUtils;
import jakarta.transaction.Transactional;
import org.goafabric.core.mrc.controller.vo.Encounter;
import org.goafabric.core.mrc.repository.EncounterRepository;
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
                StringUtils.isEmpty(text)
                        ? encounterRepository.findAll()
                        : encounterRepository.findByPatientIdAndMedicalRecords_DisplayContainsIgnoreCase(patientId, text));
    }

}
