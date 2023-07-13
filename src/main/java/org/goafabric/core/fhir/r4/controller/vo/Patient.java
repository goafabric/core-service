
package org.goafabric.core.fhir.r4.controller.vo;

import java.util.List;

public record Patient (
    String resourceType,

    String id,

    Meta meta,
    Boolean active,
    List<Identifier> identifier,

    String gender,
    String birthDate,

    List<HumanName> name,
    List<Telecom> telecom,
    List<Address> address
) {
    public String resourceType() {
        return "Patient";
    }
}
