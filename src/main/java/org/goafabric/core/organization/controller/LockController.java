package org.goafabric.core.organization.controller;

import org.goafabric.core.organization.controller.dto.Lock;
import org.goafabric.core.organization.logic.LockLogic;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "/locks", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class LockController {
    private final LockLogic logic;

    public LockController(LockLogic logic) {
        this.logic = logic;
    }

    @GetMapping("acquireLockByKey")
    public Lock acquireLockByKey(@RequestParam String lockKey) {
        return logic.acquireLockByKey(lockKey);
    }

    @DeleteMapping("removeLockById")
    public void removeLockById(@RequestParam String lockId) {
        logic.removeLockById(lockId);
    }
}
