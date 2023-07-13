
package org.goafabric.core.fhir.controller.vo;

import java.util.ArrayList;
import java.util.List;

public class Practitioner {

    public String id;

    public final Meta meta = new Meta();
    public final String resourceType = "Practitioner";
    public final Boolean active = Boolean.TRUE;
    public final List<Identifier> identifier = new ArrayList<>();


    public String gender;
    public String birthDate;

    public List<HumanName> name;
    public List<Telecom> telecom;
    public List<Address> address;

}
