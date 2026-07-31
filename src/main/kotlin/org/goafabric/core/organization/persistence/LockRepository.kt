package org.goafabric.core.organization.persistence

import io.quarkus.hibernate.panache.PanacheRepository
import jakarta.data.repository.Find
import org.goafabric.core.organization.persistence.entity.LockEo
import java.util.Optional

interface LockRepository : PanacheRepository.Managed<LockEo, String> {
    @Find
    fun findByLockKey(lockKey: String): Optional<LockEo>

    fun save(lockEo: LockEo): LockEo {
        return session.merge(lockEo)
    }

    fun deleteByLockKey(lockKey: String) {
        session.createQuery("DELETE FROM LockEo l WHERE l.lockKey = :lockKey")
            .setParameter("lockKey", lockKey)
            .executeUpdate()
    }
}
