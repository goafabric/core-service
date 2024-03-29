package org.goafabric.core.organization.logic.mapper;

import org.goafabric.core.organization.controller.dto.Practitioner;
import org.goafabric.core.organization.persistence.entity.PractitionerEo;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PractitionerMapper {
    Practitioner map(PractitionerEo value);

    PractitionerEo map(Practitioner value);

    List<Practitioner> map(List<PractitionerEo> value);

}
