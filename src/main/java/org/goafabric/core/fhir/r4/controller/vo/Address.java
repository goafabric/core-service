
package org.goafabric.core.fhir.r4.controller.vo;

import java.util.List;

public record Address (
    String id,
    String use,
    List<String> line,
    String city,
    String postalCode,
    String state,
    String country
) {
    public String getStreet() {
        return line() != null ? String.join("", line) : " ";
    }
}
