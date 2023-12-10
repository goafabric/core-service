package org.goafabric.core.medicalrecords.logic;

import io.micrometer.common.util.StringUtils;
import org.goafabric.core.medicalrecords.controller.dto.Encounter;
import org.goafabric.core.medicalrecords.repository.EncounterRepository;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
//@Profile("mongodb")
public class EncounterLogicMongo implements EncounterLogic {
    private final EncounterMapper encounterMapper;

    private final EncounterRepository encounterRepository;

    public EncounterLogicMongo(EncounterMapper encounterMapper, EncounterRepository encounterRepository) {
        this.encounterMapper = encounterMapper;
        this.encounterRepository = encounterRepository;
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

/*
simple queries
{ "$text" : { "$search" : "eat"}}

{ "medicalRecords.type" : "ANAMNESIS"}

aggregation to filter embedded collection:

[
  {
    $match: { "medicalRecords.type": "ANAMNESIS" }
  },
  {
    $addFields: {
      medicalRecords: {
        $filter: {
          input: "$medicalRecords",
          as: "record",
          cond: { $eq: ["$$record.type", "ANAMNESIS"] }
        }
      }
    }
  }
]
*/
