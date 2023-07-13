
package org.goafabric.core.fhir.r4.controller.vo;

public record Telecom (
    String id,
    String system,
    String value,
    String use
) {}
