package org.goafabric.core.medicalrecords.logic.jpa.mapper

import org.goafabric.core.medicalrecords.controller.dto.MedicalRecord
import org.goafabric.core.medicalrecords.persistence.jpa.entity.MedicalRecordEo
import org.mapstruct.Mapper
import org.mapstruct.ReportingPolicy

@Mapper(componentModel = "cdi", unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface MedicalRecordMapper {
    fun map(value: MedicalRecordEo): MedicalRecord
    fun map(value: MedicalRecord): MedicalRecordEo
    fun map(values: List<MedicalRecordEo>): List<MedicalRecord>
}
