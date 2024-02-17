package org.goafabric.core.organization.logic.mapper;

import org.goafabric.core.organization.controller.dto.Lock;
import org.goafabric.core.organization.repository.entity.LockEo;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LockMapper {
    Lock map(LockEo value);

    LockEo map(Lock value);

    List<Lock> map(List<LockEo> value);
}
