package org.goafabric.core.data.logic.mapper;

import org.goafabric.core.data.controller.vo.Practitioner;
import org.goafabric.core.data.repository.entity.PractitionerEo;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PractitionerMapper extends WorkaroundMapper {
    Practitioner map(PractitionerEo value);

    PractitionerEo map(Practitioner value);

    List<Practitioner> map(List<PractitionerEo> value);

    List<Practitioner> map(Iterable<PractitionerEo> value);
}
