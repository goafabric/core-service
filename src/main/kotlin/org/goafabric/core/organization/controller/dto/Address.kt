package org.goafabric.core.organization.controller.dto

data class Address(
    var id: String? = null,
    var version: Long? = null,
    var use: String? = null,
    var street: String? = null,
    var city: String? = null,
    var postalCode: String? = null,
    var state: String? = null,
    var country: String? = null
)
