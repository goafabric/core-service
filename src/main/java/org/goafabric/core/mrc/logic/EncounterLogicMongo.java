package org.goafabric.core.mrc.logic;

import io.micrometer.common.util.StringUtils;
import org.goafabric.core.mrc.controller.vo.Encounter;
import org.goafabric.core.mrc.repository.EncounterRepository;
import org.goafabric.core.mrc.repository.MedicalRecordRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Profile("mongodb")
public class EncounterLogicMongo implements EncounterLogic {
    private final EncounterMapper encounterMapper;

    private final EncounterRepository encounterRepository;

    private final MedicalRecordRepository medicalRecordRepository;

    public EncounterLogicMongo(EncounterMapper encounterMapper, EncounterRepository encounterRepository, MedicalRecordRepository medicalRecordRepository) {
        this.encounterMapper = encounterMapper;
        this.encounterRepository = encounterRepository;
        this.medicalRecordRepository = medicalRecordRepository;
    }

    public void save(Encounter encounter) {
        var encounterEo = encounterMapper.map(encounter);
        //encounterEo.medicalRecords = StreamSupport.stream(medicalRecordRepository.saveAll(encounterEo.medicalRecords).spliterator(), false).toList();
        encounterRepository.save(encounterEo);
    }

    public List<Encounter> findByPatientIdAndText(String patientId, String text) {
        return encounterMapper.map(
                StringUtils.isEmpty(text)
                        ? encounterRepository.findAll()
                        : encounterRepository.findAllByPatientId(patientId, new TextCriteria().matchingAny(text)));
    }

}
