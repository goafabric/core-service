package org.goafabric.core.organization.controller.dto

data class Address(
    val id: String? = null,
    val version: Long? = null,
    val use: String? = null,
    val street: String? = null,
    val city: String? = null,
    val postalCode: String? = null,
    val state: String? = null,
    val country: String? = null
)
