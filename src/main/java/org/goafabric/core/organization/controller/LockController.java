package org.goafabric.core.organization.controller;

import org.goafabric.core.organization.controller.dto.Lock;
import org.goafabric.core.organization.logic.LockLogic;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/lock", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class LockController {
    private final LockLogic logic;

    public LockController(LockLogic logic) {
        this.logic = logic;
    }

    public Lock acquireLock(String lockKey) {
        return logic.acquireLock(lockKey);
    }

    public void removeLock(String lockKey) {
        logic.removeLock(lockKey);
    }
}
