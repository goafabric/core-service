package org.goafabric.core.data.logic;

import org.goafabric.core.data.controller.dto.Person;
import org.goafabric.core.data.persistence.domain.PersonBo;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PersonMapper {
    Person map(PersonBo person);

    PersonBo map(Person person);

    List<Person> map(List<PersonBo> countries);
}
