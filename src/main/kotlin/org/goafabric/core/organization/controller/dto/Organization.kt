package org.goafabric.core.organization.controller.dto

data class Organization(
    var id: String? = null,
    var version: Long? = null,
    var name: String? = null,
    var bsnr: String? = null,
    var address: MutableList<Address> = mutableListOf(),
    var contactPoint: MutableList<ContactPoint> = mutableListOf()
)
