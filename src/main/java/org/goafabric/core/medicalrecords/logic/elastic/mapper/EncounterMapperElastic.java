package org.goafabric.core.medicalrecords.logic.elastic.mapper;


import org.goafabric.core.medicalrecords.controller.dto.Encounter;
import org.goafabric.core.medicalrecords.repository.elastic.repository.entity.EncounterElo;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EncounterMapperElastic {
    Encounter map(EncounterElo value);

    EncounterElo map(Encounter value);

    List<Encounter> map(List<EncounterElo> value);

    List<Encounter> map(Iterable<EncounterElo> value);
}
