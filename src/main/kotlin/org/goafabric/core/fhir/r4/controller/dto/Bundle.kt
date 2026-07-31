package org.goafabric.core.fhir.r4.controller.dto

data class Bundle<T>(
    val id: String? = null,
    val resourceType: String = "Bundle",
    val entry: List<BundleEntryComponent<T>> = emptyList()
) {
    data class BundleEntryComponent<T>(
        val resource: T,
        val fullUrl: String
    )
}
