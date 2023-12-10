
package org.goafabric.core.fhir.r4.controller.dto;
public record Meta (
    String versionId,
    String lastUpdated,
    String source
) {
    public Meta() {
        this("", "", "");
    }
}

