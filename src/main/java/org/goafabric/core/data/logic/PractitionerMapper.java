package org.goafabric.core.data.logic;

import org.goafabric.core.data.controller.dto.Practitioner;
import org.goafabric.core.data.persistence.domain.PractitionerBo;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PractitionerMapper extends WorkaroundMapper {
    Practitioner map(PractitionerBo value);

    PractitionerBo map(Practitioner value);

    List<Practitioner> map(List<PractitionerBo> value);

}
