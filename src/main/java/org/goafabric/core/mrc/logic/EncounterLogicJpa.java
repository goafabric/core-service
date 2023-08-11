package org.goafabric.core.mrc.logic;

import io.micrometer.common.util.StringUtils;
import jakarta.transaction.Transactional;
import org.goafabric.core.mrc.controller.vo.Encounter;
import org.goafabric.core.mrc.repository.EncounterRepository;
import org.goafabric.core.mrc.repository.MedicalRecordRepository;
import org.goafabric.core.mrc.repository.entity.EncounterEo;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Transactional
@Profile("jpa")
public class EncounterLogicJpa implements EncounterLogic{

    private final EncounterMapper encounterMapper;

    private final EncounterRepository encounterRepository;

    private final MedicalRecordRepository medicalRecordRepository;

    public EncounterLogicJpa(EncounterMapper encounterMapper, EncounterRepository encounterRepository, MedicalRecordRepository medicalRecordRepository) {
        this.encounterMapper = encounterMapper;
        this.encounterRepository = encounterRepository;
        this.medicalRecordRepository = medicalRecordRepository;
    }

    public void save(Encounter encounter) {
        var encounterEo = encounterRepository.save(encounterMapper.map(encounter));

        encounterEo.medicalRecords.stream().forEach(condition -> condition.encounterId = encounterEo.id);
        medicalRecordRepository.saveAll(encounterEo.medicalRecords);
    }

    public List<Encounter> findByPatientIdAndText(String patientId, String text) {
        var encounters = encounterRepository.findByPatientId(patientId);
        return encounters.stream().map(encounter -> findByEncounterIdAndText(encounter, text)).toList();
    }

    private Encounter findByEncounterIdAndText(EncounterEo encounter, String text) {
        return encounterMapper.map(
                new EncounterEo(
                    encounter.id,
                    encounter.patientId,
                    encounter.encounterDate,
                    StringUtils.isEmpty(text)
                            ? medicalRecordRepository.findByEncounterId(encounter.id)
                            : medicalRecordRepository.findByEncounterIdAndDisplayContainsIgnoreCase(encounter.id, text)
                )
        );
    }
}
