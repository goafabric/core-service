package org.goafabric.core.fhir.r4.controller

import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import org.goafabric.core.fhir.r4.controller.dto.MetaData

@Path("fhir/r4/metadata")
@Produces("application/json", "application/fhir+json")
class MetaDataController {

    @GET
    fun getMetadata(): MetaData = MetaData("CapabilityStatement", "core-service")
}
