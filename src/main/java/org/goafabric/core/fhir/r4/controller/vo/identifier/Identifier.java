
package org.goafabric.core.fhir.r4.controller.vo.identifier;

public record Identifier (
    IdentifierUse use,
    String value,
    String system,
    Type type
) {}
