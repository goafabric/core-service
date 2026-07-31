package org.goafabric.core.organization.controller.dto

data class Role(
    var id: String? = null,
    var version: Long? = null,
    var name: String? = null,
    var permissions: MutableList<Permission> = mutableListOf()
)
