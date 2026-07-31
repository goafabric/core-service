package org.goafabric.core.organization.controller

import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import org.goafabric.core.organization.controller.dto.Organization
import org.goafabric.core.organization.logic.OrganizationLogic

@Path("/organizations")
@Produces(MediaType.APPLICATION_JSON)
class OrganizationController(private val logic: OrganizationLogic) {

    @DELETE
    @Path("deleteById/{id}")
    fun deleteById(@PathParam("id") id: String) {
        logic.deleteById(id)
    }

    @GET
    @Path("getById/{id}")
    fun getById(@PathParam("id") id: String): Organization = logic.getById(id)

    @GET
    @Path("findByName")
    fun findByName(@QueryParam("name") name: String?): List<Organization> =
        logic.findByName(name ?: "")

    @POST
    @Path("save")
    @Consumes(MediaType.APPLICATION_JSON)
    fun save(organization: Organization): Organization = logic.save(organization)
}
