package org.goafabric.core.fhir.r4.r4.controller;

import org.goafabric.core.fhir.r4.controller.vo.metadata.MetaData;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "fhir/r4/metadata", produces = {MediaType.APPLICATION_JSON_VALUE, "application/fhir+json"})
public class MetaDataController {

    @GetMapping
    public MetaData getMetadata() {
        var metaData = new MetaData();
        metaData.resourceType = "CapabilityStatement";
        metaData.name = "core-service";
        return metaData;

    }
}
