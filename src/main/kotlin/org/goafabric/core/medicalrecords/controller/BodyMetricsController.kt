package org.goafabric.core.medicalrecords.controller

import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import org.goafabric.core.medicalrecords.controller.dto.BodyMetrics
import org.goafabric.core.medicalrecords.controller.dto.MedicalRecord
import org.goafabric.core.medicalrecords.logic.jpa.BodyMetricsLogic

@Path("/bodymetrics")
@Produces(MediaType.APPLICATION_JSON)
class BodyMetricsController(private val logic: BodyMetricsLogic) {

    @GET
    @Path("getById/{id}")
    fun getById(@PathParam("id") id: String): BodyMetrics = logic.getById(id)

    @POST
    @Path("save")
    @Consumes(MediaType.APPLICATION_JSON)
    fun save(bodyMetrics: BodyMetrics): MedicalRecord = logic.save(bodyMetrics)

    @GET
    @Path("delete/{id}")
    fun delete(@PathParam("id") id: String) {
        throw IllegalStateException("Deletion not allowed here, should happen via Medical Record")
    }
}
