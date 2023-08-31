package org.goafabric.core.organization.logic.mapper;

import org.goafabric.core.organization.controller.vo.User;
import org.goafabric.core.organization.repository.entity.UserEo;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    User map(UserEo value);

    UserEo map(User value);

    List<User> map(List<UserEo> value);
}
