package org.goafabric.core.fhir.r4.controller

import jakarta.ws.rs.*
import org.goafabric.core.fhir.r4.controller.dto.Bundle
import org.goafabric.core.fhir.r4.controller.dto.Organization
import org.goafabric.core.fhir.r4.logic.mapper.FhirOrganizationMapper
import org.goafabric.core.organization.logic.OrganizationLogic

@Path("fhir/r4/Organization")
@Produces("application/json", "application/fhir+json")
class OrganizationFhirController(
    private val logic: OrganizationLogic,
    private val mapper: FhirOrganizationMapper
) {
    @POST
    @Consumes("application/json", "application/fhir+json")
    fun create(organization: Organization) {
        throw IllegalStateException("NYI")
    }

    @DELETE
    @Path("/{id}")
    fun delete(@PathParam("id") id: String) = logic.deleteById(id)

    @GET
    @Path("/{id}")
    fun getById(@PathParam("id") id: String): Organization = mapper.map(logic.getById(id))

    @GET
    fun search(@QueryParam("name") name: String?): Bundle<Organization> {
        val organizations = mapper.map(logic.findByName(name ?: ""))
        return Bundle(entry = organizations.map { o -> Bundle.BundleEntryComponent(o, "Organization/${o.id}") })
    }
}
