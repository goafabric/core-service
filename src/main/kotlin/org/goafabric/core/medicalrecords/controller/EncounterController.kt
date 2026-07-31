package org.goafabric.core.medicalrecords.controller

import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import org.goafabric.core.medicalrecords.controller.dto.Encounter
import org.goafabric.core.medicalrecords.logic.EncounterLogic

@Path("/encounters")
@Produces(MediaType.APPLICATION_JSON)
class EncounterController(private val logic: EncounterLogic) {

    @GET
    @Path("findByPatientIdAndDisplay")
    fun findByPatientIdAndDisplay(
        @QueryParam("patientId") patientId: String?,
        @QueryParam("display") display: String?
    ): List<Encounter> = logic.findByPatientIdAndDisplay(patientId ?: "", display ?: "")

    @POST
    @Path("save")
    @Consumes(MediaType.APPLICATION_JSON)
    fun save(encounter: Encounter): Encounter = logic.save(encounter)
}
