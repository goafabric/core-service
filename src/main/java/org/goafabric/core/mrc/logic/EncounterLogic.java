package org.goafabric.core.mrc.logic;

import jakarta.transaction.Transactional;
import org.goafabric.core.mrc.controller.vo.Encounter;
import org.goafabric.core.mrc.repository.AnamnesisRepository;
import org.goafabric.core.mrc.repository.ConditionRepository;
import org.goafabric.core.mrc.repository.EncounterRepository;
import org.goafabric.core.mrc.repository.entity.EncounterEo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Transactional
public class EncounterLogic {

    private final EncounterMapper encounterMapper;

    private final EncounterRepository encounterRepository;

    private final AnamnesisRepository anamnesisRepository;

    private final ConditionRepository conditionRepository;

    @Value("${spring.profiles.active}") private String profile;

    public EncounterLogic(EncounterMapper encounterMapper, EncounterRepository encounterRepository, AnamnesisRepository anamnesisRepository, ConditionRepository conditionRepository) {
        this.encounterMapper = encounterMapper;
        this.encounterRepository = encounterRepository;
        this.anamnesisRepository = anamnesisRepository;
        this.conditionRepository = conditionRepository;
    }

    public void save(Encounter encounter) {
        var encounterEo = encounterRepository.save(encounterMapper.map(encounter));

        encounterEo.anamnesises.stream().forEach(anamesis -> anamesis.encounterId = encounterEo.id);
        encounterEo.conditions.stream().forEach(condition -> condition.encounterId = encounterEo.id);
        anamnesisRepository.saveAll(encounterEo.anamnesises);
        conditionRepository.saveAll(encounterEo.conditions);
    }

    public List<Encounter> findByPatientIdAndText(String patientId, String text) {
        var encounters = encounterRepository.findByPatientId(patientId);
        return encounters.stream().map(encounter -> findByEncounterIdAndText(encounter, text)).toList();
        //return encounterMapper.map(encounterRepository.findAllByPatientId(patientId, (new TextCriteria().matchingAny(text))));
    }

    private Encounter findByEncounterIdAndText(EncounterEo encounter, String text) {
        return encounterMapper.map(
                new EncounterEo(
                    encounter.id,
                    encounter.patientId,
                    encounter.encounterDate,
                    anamnesisRepository.findByEncounterIdAndTextContains(encounter.id, text),
                    conditionRepository.findByEncounterIdAndDisplayContains(encounter.id, text)
                )
        );
    }
}
