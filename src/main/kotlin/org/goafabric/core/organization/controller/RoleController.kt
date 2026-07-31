package org.goafabric.core.organization.controller

import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import org.goafabric.core.organization.controller.dto.Role
import org.goafabric.core.organization.logic.RoleLogic

@Path("/roles")
@Produces(MediaType.APPLICATION_JSON)
class RoleController(private val logic: RoleLogic) {

    @DELETE
    @Path("deleteById/{id}")
    fun deleteById(@PathParam("id") id: String) = logic.deleteById(id)

    @GET
    @Path("getById/{id}")
    fun getById(@PathParam("id") id: String): Role = logic.getById(id)

    @GET
    @Path("findByName")
    fun findByName(@QueryParam("name") name: String?): List<Role> = logic.findByName(name ?: "")

    @POST
    @Path("save")
    @Consumes(MediaType.APPLICATION_JSON)
    fun save(role: Role): Role = logic.save(role)
}
