package org.goafabric.core.fhir.r4.controller;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;

 class ClientFactory {
    private ClientFactory() {
    }

     static IGenericClient createClient(String port) {
        return FhirContext.forR4().newRestfulGenericClient(
                "http://localhost:" + port + "/fhir/r4");
    }
}
