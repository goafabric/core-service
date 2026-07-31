package org.goafabric.core.organization.controller

import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import org.goafabric.core.organization.controller.dto.Practitioner
import org.goafabric.core.organization.logic.PractitionerLogic

@Path("/practitioners")
@Produces(MediaType.APPLICATION_JSON)
class PractitionerController(private val logic: PractitionerLogic) {

    @DELETE
    @Path("deleteById/{id}")
    fun deleteById(@PathParam("id") id: String) {
        logic.deleteById(id)
    }

    @GET
    @Path("getById/{id}")
    fun getById(@PathParam("id") id: String): Practitioner = logic.getById(id)

    @GET
    @Path("findByGivenName")
    fun findByGivenName(@QueryParam("givenName") givenName: String?): List<Practitioner> =
        logic.findByGivenName(givenName ?: "")

    @GET
    @Path("findByFamilyName")
    fun findByFamilyName(@QueryParam("familyName") familyName: String?): List<Practitioner> =
        logic.findByFamilyName(familyName ?: "")

    @POST
    @Path("save")
    @Consumes(MediaType.APPLICATION_JSON)
    fun save(practitioner: Practitioner): Practitioner = logic.save(practitioner)
}
