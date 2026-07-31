package org.goafabric.core.fhir.r4.controller.dto.identifier

data class Identifier(
    val use: IdentifierUse? = null,
    val type: Type? = null,
    val value: String? = null,
    val system: String? = null
)
