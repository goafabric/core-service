package org.goafabric.core.fhir.r4.controller

import jakarta.ws.rs.*
import org.goafabric.core.fhir.r4.controller.dto.Bundle
import org.goafabric.core.fhir.r4.controller.dto.Patient
import org.goafabric.core.fhir.r4.logic.mapper.FhirPatientMapper
import org.goafabric.core.organization.logic.PatientLogic

@Path("fhir/r4/Patient")
@Produces("application/json", "application/fhir+json")
class PatientFhirController(
    private val logic: PatientLogic,
    private val mapper: FhirPatientMapper
) {
    @POST
    @Consumes("application/json", "application/fhir+json")
    fun create(patient: Patient) {
        throw IllegalStateException("NYI")
    }

    @DELETE
    @Path("/{id}")
    fun delete(@PathParam("id") id: String) = logic.deleteById(id)

    @GET
    @Path("/{id}")
    fun getById(@PathParam("id") id: String): Patient = mapper.map(logic.getById(id))

    @GET
    fun search(@QueryParam("family") familyName: String?): Bundle<Patient> {
        val patients = mapper.map(logic.findByFamilyName(familyName ?: ""))
        return Bundle(entry = patients.map { p -> Bundle.BundleEntryComponent(p, "Patient/${p.id}") })
    }
}
