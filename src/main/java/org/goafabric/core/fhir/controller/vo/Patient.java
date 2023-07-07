
package org.goafabric.core.fhir.controller.vo;

import java.util.List;

public class Patient {

    public String id;
    public Meta meta;

    public final String resourceType = "Patient";

    public String gender;
    public String birthDate;

    public List<Identifier> identifier;
    public List<HumanName> name;
    public List<Telecom> telecom;
    public List<Address> address;

}
