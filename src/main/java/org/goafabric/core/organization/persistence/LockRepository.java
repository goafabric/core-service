package org.goafabric.core.organization.persistence;

import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import org.goafabric.core.organization.persistence.entity.LockEo;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface LockRepository extends CrudRepository<LockEo, String> {
    @Lock(LockModeType.PESSIMISTIC_READ)
    @QueryHints({@QueryHint(name = "jakarta.persistence.lock.timeout", value = "1000")})
    Optional<LockEo> findByLockKey(String lockKey);

    void deleteByLockKey(String lockKey);
}

