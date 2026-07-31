package org.goafabric.core.fhir.r4.controller.dto

data class Address(
    val id: String? = null,
    val use: String? = null,
    val line: List<String> = emptyList(),
    val city: String? = null,
    val postalCode: String? = null,
    val state: String? = null,
    val country: String? = null
) {
    fun getStreet(): String = if (line.isNotEmpty()) line.joinToString("") else " "
}
