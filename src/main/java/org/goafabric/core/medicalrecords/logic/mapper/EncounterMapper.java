package org.goafabric.core.medicalrecords.logic.mapper;

import org.goafabric.core.medicalrecords.controller.dto.Encounter;
import org.goafabric.core.medicalrecords.repository.entity.EncounterEo;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EncounterMapper {
    Encounter map(EncounterEo value);

    EncounterEo map(Encounter value);

    List<Encounter> map(List<EncounterEo> value);

    List<Encounter> map(Iterable<EncounterEo> value);
}
