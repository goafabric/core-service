package org.goafabric.core.organization.controller.dto.types

enum class ContactPointSystem(val value: String) {
    PHONE("phone"),
    FAX("fax"),
    EMAIL("email"),
    PAGER("pager"),
    URL("url"),
    SMS("sms"),
    OTHER("other"),
    NULL("null")
}
