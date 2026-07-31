package org.goafabric.core.organization.controller

import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import org.goafabric.core.organization.controller.dto.Lock
import org.goafabric.core.organization.logic.LockLogic

@Path("/locks")
@Produces(MediaType.APPLICATION_JSON)
class LockController(private val logic: LockLogic) {

    @GET
    @Path("acquireLockByKey")
    fun acquireLockByKey(@QueryParam("lockKey") lockKey: String): Lock =
        logic.acquireLockByKey(lockKey)

    @DELETE
    @Path("removeLockById")
    fun removeLockById(@QueryParam("lockId") lockId: String) =
        logic.removeLockById(lockId)
}
