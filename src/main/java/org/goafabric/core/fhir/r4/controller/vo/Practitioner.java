
package org.goafabric.core.fhir.r4.controller.vo;

import org.goafabric.core.fhir.r4.controller.vo.identifier.Identifier;

import java.util.List;

//https://simplifier.net/verordnungssoftware-schnittstelle/~resources?category=Example&exampletype=Practitioner
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
