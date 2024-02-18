package org.goafabric.core.ui.adapter;

import org.goafabric.core.organization.controller.LockController;
import org.goafabric.core.organization.controller.dto.Lock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class LockAdapter {
    private final LockController controller;
    private final Boolean frontendLockingEnabled;

    public LockAdapter(LockController lockController, @Value("${frontend.locking.enabled}") Boolean frontendLockingEnabled) {
        this.controller = lockController;
        this.frontendLockingEnabled = frontendLockingEnabled;
    }

    public Lock acquireLock(String lockKey) {
        return frontendLockingEnabled ? controller.acquireLockByKey(lockKey)
                : new Lock(null, false, null, null, null);
    }

    public void removeLock(String lockKey) {
        controller.removeLockById(lockKey);
    }

}
