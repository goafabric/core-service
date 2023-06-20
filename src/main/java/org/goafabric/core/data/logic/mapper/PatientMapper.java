package org.goafabric.core.data.logic.mapper;

import org.goafabric.core.data.controller.dto.Patient;
import org.goafabric.core.data.persistence.domain.PatientBo;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PatientMapper extends WorkaroundMapper {
    Patient map(PatientBo value);

    PatientBo map(Patient value);

    List<Patient> map(List<PatientBo> value);

}
