package org.goafabric.core.organization.controller.dto.types

enum class AddressUse(val value: String?) {
    HOME("home"),
    WORK("work"),
    TEMP("temp"),
    OLD("old"),
    BILLING("billing"),
    NULL(null)
}
