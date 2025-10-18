/*
package org.goafabric.core.medicalrecords.logic.elastic.mapper;


import org.goafabric.core.medicalrecords.controller.dto.MedicalRecord;
import org.goafabric.core.medicalrecords.persistence.elastic.repository.entity.MedicalRecordElo;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;

import java.util.List;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MedicalRecordMapperElastic {
    MedicalRecord map(MedicalRecordElo value);

    MedicalRecordElo map(MedicalRecord value);

    List<MedicalRecord> map(Iterable<MedicalRecordElo> value);

    default List<MedicalRecord> map(SearchHits<MedicalRecordElo> hits) {
        return map(hits.stream().map(SearchHit::getContent).toList());
    }
}

 */
