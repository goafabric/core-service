package org.goafabric.core.organization.logic;

import org.goafabric.core.extensions.TenantContext;
import org.goafabric.core.organization.controller.dto.Lock;
import org.goafabric.core.organization.persistence.LockRepository;
import org.goafabric.core.organization.persistence.entity.LockEo;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
@Transactional
public class LockLogic {
    private final LockRepository repository;

    public LockLogic(LockRepository repository) {
        this.repository = repository;
    }

    public Lock acquireLockByKey(String lockKey) {
        var lockFound = repository.findByLockKey(lockKey);
        if (lockFound.isPresent()) {  //we could check here if lock has expired after certain amount of time and overwrite, yet this is dangerous
            var lock = lockFound.get();
            return new Lock(lock.getId(), true, lock.getLockKey(), lock.getLockTime(), lock.getUserName());
        } else {
            var lock = repository.save(new LockEo(null, lockKey, LocalDateTime.now(), TenantContext.getUserName()));
            return new Lock(lock.getId(), false, lock.getLockKey(), lock.getLockTime(), lock.getUserName());
        }
    }

    public void removeLockById(String id) { //its much saver to be removed by id, to avoid unpriviliged views to remove lock
        repository.deleteById(id);
    }
}
