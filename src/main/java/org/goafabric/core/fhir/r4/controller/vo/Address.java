
package org.goafabric.core.fhir.controller.vo;

import java.util.List;

public class Address {
    public String id;

    public String use;

    public List<String> line;
    public String city;
    public String postalCode;

    public String state;
    public String country;

    public String getStreet() {
        return line != null ? String.join("", line) : " ";
    }
}
