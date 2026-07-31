package org.goafabric.core.organization.controller

import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import org.goafabric.core.organization.controller.dto.Patient
import org.goafabric.core.organization.logic.PatientLogic

@Path("/patients")
@Produces(MediaType.APPLICATION_JSON)
class PatientController(private val logic: PatientLogic) {

    @DELETE
    @Path("deleteById/{id}")
    fun deleteById(@PathParam("id") id: String) {
        logic.deleteById(id)
    }

    @GET
    @Path("getById/{id}")
    fun getById(@PathParam("id") id: String): Patient = logic.getById(id)

    @GET
    @Path("findByGivenName")
    fun findByGivenName(@QueryParam("givenName") givenName: String?): List<Patient> =
        logic.findByGivenName(givenName ?: "")

    @GET
    @Path("findByFamilyName")
    fun findByFamilyName(@QueryParam("familyName") familyName: String?): List<Patient> =
        logic.findByFamilyName(familyName ?: "")

    @GET
    @Path("findPatientNamesByFamilyName")
    fun findPatientNamesByFamilyName(@QueryParam("search") search: String?): List<Patient> =
        logic.findPatientNamesByFamilyName(search ?: "")

    @POST
    @Path("save")
    @Consumes(MediaType.APPLICATION_JSON)
    fun save(patient: Patient): Patient = logic.save(patient)
}
