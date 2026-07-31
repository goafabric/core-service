package org.goafabric.core.organization.controller.dto

import java.time.LocalDate

data class Practitioner(
    var id: String? = null,
    var version: Long? = null,
    var givenName: String? = null,
    var familyName: String? = null,
    var gender: String? = null,
    var birthDate: LocalDate? = null,
    var lanr: String? = null,
    var address: MutableList<Address> = mutableListOf(),
    var contactPoint: MutableList<ContactPoint> = mutableListOf()
)
