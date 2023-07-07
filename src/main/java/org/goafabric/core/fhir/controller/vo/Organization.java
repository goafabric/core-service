
package org.goafabric.core.fhir.controller.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Organization {

    private String id;
    public Meta meta;

    @Transient
    private final String resourceType = "Organization";

    public Boolean active;
    private String name;

    private List<Identifier> identifier;
    private List<Telecom> telecom;
    private List<Address> address;

}
