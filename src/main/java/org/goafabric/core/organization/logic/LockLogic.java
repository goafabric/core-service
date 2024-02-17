package org.goafabric.core.organization.logic;

import org.goafabric.core.extensions.HttpInterceptor;
import org.goafabric.core.organization.controller.dto.Lock;
import org.goafabric.core.organization.logic.mapper.LockMapper;
import org.goafabric.core.organization.repository.LockRepository;
import org.goafabric.core.organization.repository.entity.LockEo;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Component
@Transactional
public class LockLogic {
    private final LockRepository repository;
    private final LockMapper mapper;

    public LockLogic(LockRepository repository, LockMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public Lock acquireLock(String lockKey) {
        var lock = repository.findByLockKey(lockKey);
        if (lock.isPresent()) {
            return mapper.map(lock.get());
        } else {
            repository.save(new LockEo(null, lockKey, LocalDate.now(), HttpInterceptor.getUserName()));
            return new Lock(null, false, null, null, null);
        }
    }
    
    public void removeLock(String lockKey) {
        repository.deleteByLockKey(lockKey);
    }
}
