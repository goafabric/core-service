package org.goafabric.core.organization.repository;

import jakarta.persistence.LockModeType;
import org.goafabric.core.organization.repository.entity.LockEo;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface LockRepository extends CrudRepository<LockEo, String> {
    @Lock(LockModeType.PESSIMISTIC_READ)
    Optional<LockEo> findByLockKey(String lockKey);

    void deleteByLockKey(String lockKey);
}

