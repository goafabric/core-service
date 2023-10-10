
package org.goafabric.core.fhir.r4.controller.vo.identifier;

public record Identifier (
    IdentifierUse use,
    Type type,
    String value,
    String system
) {}
