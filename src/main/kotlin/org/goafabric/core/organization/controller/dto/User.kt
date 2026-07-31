package org.goafabric.core.organization.controller.dto

data class User(
    var id: String? = null,
    var version: Long? = null,
    var practitionerId: String? = null,
    var name: String? = null,
    var roles: MutableList<Role> = mutableListOf()
)
