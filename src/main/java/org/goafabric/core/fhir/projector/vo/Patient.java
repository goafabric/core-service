
package org.goafabric.core.fhir.projector.vo;

import java.util.ArrayList;
import java.util.List;

public class Patient {

    public String id;

    public final Meta meta = new Meta();
    public final String resourceType = "Patient";
    public final Boolean active = Boolean.TRUE;
    public final List<Identifier> identifier = new ArrayList<>();

    public String gender;
    public String birthDate;

    public List<HumanName> name;
    public List<Telecom> telecom;
    public List<Address> address;

}
