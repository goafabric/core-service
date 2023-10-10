
package org.goafabric.core.fhir.r4.controller.vo;

import org.goafabric.core.fhir.r4.controller.vo.identifier.Identifier;

import java.util.List;


public record Organization (

    String id,

    Meta meta,
    String resourceType,
    Boolean active,
    List<Identifier> identifier,

    String name,

    List<Telecom> telecom,
    List<Address> address
) {
    public String resourceType() {
        return "Organization";
    }
}
