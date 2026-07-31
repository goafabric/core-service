package org.goafabric.core.medicalrecords.controller

import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import org.goafabric.core.medicalrecords.controller.dto.MedicalRecord
import org.goafabric.core.medicalrecords.logic.MedicalRecordLogic

@Path("/medicalrecords")
@Produces(MediaType.APPLICATION_JSON)
class MedicalRecordController(private val logic: MedicalRecordLogic) {

    @GET
    @Path("getById/{id}")
    fun getById(@PathParam("id") id: String): MedicalRecord = logic.getById(id)

    @POST
    @Path("save")
    @Consumes(MediaType.APPLICATION_JSON)
    fun save(medicalRecord: MedicalRecord): MedicalRecord = logic.save(medicalRecord)

    @GET
    @Path("delete/{id}")
    fun delete(@PathParam("id") id: String) = logic.delete(id)
}
