package org.goafabric.core.organization.controller

import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import org.goafabric.core.organization.controller.dto.User
import org.goafabric.core.organization.controller.dto.UserInfo
import org.goafabric.core.organization.controller.dto.types.PermissionCategory
import org.goafabric.core.organization.controller.dto.types.PermissionType
import org.goafabric.core.organization.logic.UserLogic

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
class UserController(private val logic: UserLogic) {

    @DELETE
    @Path("deleteById/{id}")
    fun deleteById(@PathParam("id") id: String) = logic.deleteById(id)

    @GET
    @Path("getById/{id}")
    fun getById(@PathParam("id") id: String): User = logic.getById(id)

    @GET
    @Path("findByName")
    fun findByName(@QueryParam("name") name: String?): List<User> = logic.findByName(name ?: "")

    @GET
    @Path("hasPermission")
    fun hasPermission(
        @QueryParam("name") name: String,
        @QueryParam("category") category: PermissionCategory,
        @QueryParam("type") type: PermissionType
    ): Boolean = logic.hasPermission(name, category, type)

    @POST
    @Path("save")
    @Consumes(MediaType.APPLICATION_JSON)
    fun save(user: User): User = logic.save(user)

    @GET
    @Path("getUserInfo")
    fun getUserInfo(): UserInfo = logic.getUserInfo()
}
