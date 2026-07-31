package org.goafabric.core.fhir.r4.controller.dto

import org.goafabric.core.fhir.r4.controller.dto.identifier.Identifier

data class Organization(
    var id: String? = null,
    var resourceType: String = "Organization",
    var meta: Meta? = null,
    var active: Boolean? = null,
    var identifier: MutableList<Identifier> = mutableListOf(),
    var name: String? = null,
    var telecom: MutableList<Telecom> = mutableListOf(),
    var address: MutableList<Address> = mutableListOf()
)
