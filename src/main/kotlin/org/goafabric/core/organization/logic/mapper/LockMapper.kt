package org.goafabric.core.organization.logic.mapper

import org.goafabric.core.organization.controller.dto.Lock
import org.goafabric.core.organization.persistence.entity.LockEo
import org.mapstruct.Mapper
import org.mapstruct.ReportingPolicy

@Mapper(componentModel = "cdi", unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface LockMapper {
    fun map(value: LockEo): Lock
    fun map(value: Lock): LockEo
    fun map(values: List<LockEo>): List<Lock>
}
