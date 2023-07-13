package org.goafabric.core.fhir.r4.controller;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.*;

import java.io.IOException;
import java.util.Base64;

public class ClientFactory {
    private ClientFactory() {
    }

    public static IGenericClient createClient(String port) {
        final IGenericClient client = FhirContext.forR4().newRestfulGenericClient(
                "http://localhost:" + port + "/fhir/r4");

        client.registerInterceptor(new IClientInterceptor() {
            @Override
            public void interceptRequest(IHttpRequest theRequest) {
                //log.info("## request log " + theRequest.toString());
                theRequest.addHeader("Authorization", "Basic "
                        + Base64.getEncoder().encodeToString("admin:admin".getBytes()));
            }

            @Override
            public void interceptResponse(IHttpResponse theResponse) throws IOException {
            }
        });
        return client;
    }
}
