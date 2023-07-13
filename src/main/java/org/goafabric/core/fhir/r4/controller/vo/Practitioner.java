
package org.goafabric.core.fhir.r4.controller.vo;

import java.util.List;

public record Practitioner (

    String id,

    Meta meta,
    String resourceType,
    Boolean active,
    List<Identifier> identifier,

    String gender,
    String birthDate,

    List<HumanName> name,
    List<Telecom> telecom,
    List<Address> address
) {
    public String resourceType() {
        return "Practitioner";
    }
}