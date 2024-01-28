package org.goafabric.core.medicalrecords.logic.elastic.mapper;


import org.goafabric.core.medicalrecords.controller.dto.MedicalRecord;
import org.goafabric.core.medicalrecords.repository.elastic.repository.entity.MedicalRecordElo;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MedicalRecordMapperElastic {
    MedicalRecord map(MedicalRecordElo value);

    MedicalRecordElo map(MedicalRecord value);

    List<MedicalRecord> map(List<MedicalRecordElo> value);

    List<MedicalRecord> map(Iterable<MedicalRecordElo> value);

    //List<MedicalRecord> map(SearchHits<MedicalRecordEo> value);

}
