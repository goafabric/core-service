package org.goafabric.core.medicalrecords.logic.jpa.mapper;

import org.goafabric.core.medicalrecords.controller.dto.MedicalRecord;
import org.goafabric.core.medicalrecords.repository.jpa.entity.MedicalRecordEo;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MedicalRecordMapper {
    MedicalRecord map(MedicalRecordEo value);

    MedicalRecordEo map(MedicalRecord value);

    List<MedicalRecord> map(Iterable<MedicalRecordEo> value);
}
