package org.goafabric.core.organization.controller.dto

data class ContactPoint(
    var id: String? = null,
    var version: Long? = null,
    var use: String? = null,
    var system: String? = null,
    var value: String? = null
)
