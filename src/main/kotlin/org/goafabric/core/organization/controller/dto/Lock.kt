package org.goafabric.core.organization.controller.dto

import java.time.LocalDateTime

data class Lock(
    val id: String? = null,
    val isLocked: Boolean? = null,
    val lockKey: String? = null,
    val lockTime: LocalDateTime? = null,
    val userName: String? = null
)
