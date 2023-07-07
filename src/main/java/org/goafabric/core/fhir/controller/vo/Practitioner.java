
package org.goafabric.core.fhir.controller.vo;

import org.springframework.data.annotation.Transient;

import java.util.List;

public class Practitioner {

    public String id;
    public Meta meta;

    @Transient
    public final String resourceType = "Practitioner";

    public Boolean active;
    public String gender;
    public String birthDate;

    public List<HumanName> name;
    public List<Identifier> identifier;
    public List<Telecom> telecom;
    public List<Address> address;

}
