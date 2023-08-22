package org.goafabric.core.organization.logic.mapper;

import org.goafabric.core.organization.controller.vo.Patient;
import org.goafabric.core.organization.repository.entity.PatientEo;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PatientMapper extends WorkaroundMapper {
    Patient map(PatientEo value);

    PatientEo map(Patient value);

    List<Patient> map(List<PatientEo> value);

}
