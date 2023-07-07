
package org.goafabric.core.fhir.controller.vo;

import org.springframework.data.annotation.Transient;

import java.util.List;


public class Organization {

    public String id;
    public Meta meta;

    @Transient
    public final String resourceType = "Organization";

    public Boolean active;
    public String name;

    public List<Identifier> identifier;
    public List<Telecom> telecom;
    public List<Address> address;

}
