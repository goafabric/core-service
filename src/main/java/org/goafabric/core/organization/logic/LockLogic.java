package org.goafabric.core.organization.logic;

import org.goafabric.core.extensions.HttpInterceptor;
import org.goafabric.core.organization.controller.dto.Lock;
import org.goafabric.core.organization.repository.LockRepository;
import org.goafabric.core.organization.repository.entity.LockEo;
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
        if (lockFound.isPresent()) {  //TDO: check if lock has expired after a certain amount of time and remove or overwrite
            var lock = lockFound.get();
            return new Lock(lock.getId(), true, lock.getLockKey(), lock.getLockTime(), lock.getUserName());
        } else {
            var lock = repository.save(new LockEo(null, lockKey, LocalDateTime.now(), HttpInterceptor.getUserName()));
            return new Lock(lock.getId(), false, lock.getLockKey(), lock.getLockTime(), lock.getUserName());
        }
    }

    public void removeLockById(String id) { //its much saver to be removed by id, to avoid unpriviliged views to remove lock
        repository.deleteById(id);
    }
}
