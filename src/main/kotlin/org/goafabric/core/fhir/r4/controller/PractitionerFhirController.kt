package org.goafabric.core.fhir.r4.controller

import jakarta.ws.rs.*
import org.goafabric.core.fhir.r4.controller.dto.Bundle
import org.goafabric.core.fhir.r4.controller.dto.Practitioner
import org.goafabric.core.fhir.r4.logic.mapper.FhirPractitionerMapper
import org.goafabric.core.organization.logic.PractitionerLogic

@Path("fhir/r4/Practitioner")
@Produces("application/json", "application/fhir+json")
class PractitionerFhirController(
    private val logic: PractitionerLogic,
    private val mapper: FhirPractitionerMapper
) {
    @POST
    @Consumes("application/json", "application/fhir+json")
    fun create(practitioner: Practitioner) {
        throw IllegalStateException("NYI")
    }

    @DELETE
    @Path("/{id}")
    fun delete(@PathParam("id") id: String) = logic.deleteById(id)

    @GET
    @Path("/{id}")
    fun getById(@PathParam("id") id: String): Practitioner = mapper.map(logic.getById(id))

    @GET
    fun search(@QueryParam("family") familyName: String?): Bundle<Practitioner> {
        val practitioners = mapper.map(logic.findByFamilyName(familyName ?: ""))
        return Bundle(entry = practitioners.map { p -> Bundle.BundleEntryComponent(p, "Practitioner/${p.id}") })
    }
}
