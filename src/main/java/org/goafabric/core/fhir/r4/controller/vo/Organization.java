
package org.goafabric.core.fhir.r4.controller.vo;

import java.util.ArrayList;
import java.util.List;


public class Organization {

    public String id;

    public final Meta meta = new Meta();
    public final String resourceType = "Organization";
    public final Boolean active = Boolean.TRUE;
    public final List<Identifier> identifier = new ArrayList<>();

    public String name;

    public List<Telecom> telecom;
    public List<Address> address;

}
