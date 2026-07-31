package org.goafabric.core.organization.logic

import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional
import org.goafabric.core.extensions.UserContext
import org.goafabric.core.organization.controller.dto.Lock
import org.goafabric.core.organization.persistence.LockRepository
import org.goafabric.core.organization.persistence.entity.LockEo
import java.time.LocalDateTime

@ApplicationScoped
@Transactional
class LockLogic(private val repository: LockRepository) {

    fun acquireLockByKey(lockKey: String): Lock {
        val lockFound = repository.findByLockKey(lockKey)
        return if (lockFound.isPresent) {
            val lock = lockFound.get()
            Lock(lock.id, true, lock.lockKey, lock.lockTime, lock.userName)
        } else {
            val lock = repository.save(LockEo(lockKey = lockKey, lockTime = LocalDateTime.now(), userName = UserContext.userName))
            Lock(lock.id, false, lock.lockKey, lock.lockTime, lock.userName)
        }
    }

    fun removeLockById(id: String) = repository.deleteById(id)
}
