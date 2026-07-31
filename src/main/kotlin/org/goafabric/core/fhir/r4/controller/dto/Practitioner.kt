package org.goafabric.core.fhir.r4.controller.dto

import org.goafabric.core.fhir.r4.controller.dto.identifier.Identifier

data class Practitioner(
    var id: String? = null,
    var meta: Meta? = null,
    var resourceType: String = "Practitioner",
    var active: Boolean? = null,
    var identifier: MutableList<Identifier> = mutableListOf(),
    var gender: String? = null,
    var birthDate: String? = null,
    var name: MutableList<HumanName> = mutableListOf(),
    var telecom: MutableList<Telecom> = mutableListOf(),
    var address: MutableList<Address> = mutableListOf()
)
