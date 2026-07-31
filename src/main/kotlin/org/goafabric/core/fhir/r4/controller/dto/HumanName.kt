package org.goafabric.core.fhir.r4.controller.dto

data class HumanName(
    val use: String? = null,
    val family: String? = null,
    val given: List<String> = emptyList()
)
