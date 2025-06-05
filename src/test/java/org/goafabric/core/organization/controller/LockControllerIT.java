package org.goafabric.core.organization.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
 class LockControllerIT {
    @Autowired
    private LockController lockController;

    @Test
    void acquireLock() {
        assertThat(lockController.acquireLockByKey("myLock").isLocked()).isFalse();

        var lock = lockController.acquireLockByKey("myLock");
        assertThat(lock).isNotNull();
        assertThat(lock.lockKey()).isEqualTo("myLock");
        assertThat(lock.isLocked()).isTrue();

        lockController.removeLockById(lock.id());
        assertThat(lockController.acquireLockByKey("myLock").isLocked()).isFalse();
    }
}
