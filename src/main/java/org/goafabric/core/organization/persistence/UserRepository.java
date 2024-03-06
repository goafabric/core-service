package org.goafabric.core.organization.persistence;

import org.goafabric.core.organization.persistence.entity.UserEo;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<UserEo, String> {
    List<UserEo> findByNameStartsWithIgnoreCase(String name);
}

