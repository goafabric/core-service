
package org.goafabric.core.fhir.r4.controller.dto;

import java.util.List;

public record HumanName (
    String use,
    String family,
    List<String> given
) {}
