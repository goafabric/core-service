package org.goafabric.core.ui.adapter;

import org.goafabric.core.organization.controller.LockController;
import org.goafabric.core.organization.controller.dto.Lock;
import org.springframework.stereotype.Component;

@Component
public class LockAdapter {
    private final LockController controller;

    public LockAdapter(LockController lockController) {
        this.controller = lockController;
    }

    public Lock acquireLock(String lockKey) {
        return controller.acquireLock(lockKey);
    }

    public void removeLock(String lockKey) {
        controller.removeLock(lockKey);
    }

}
