package org.goafabric.core.organization.persistence.entity

import jakarta.persistence.*
import org.goafabric.core.organization.controller.dto.types.PermissionCategory
import org.goafabric.core.organization.controller.dto.types.PermissionType
import org.goafabric.core.persistence.extensions.AuditTrailListener
import org.goafabric.core.persistence.extensions.KafkaPublisher

@Entity
@Table(name = "permission")
@EntityListeners(AuditTrailListener::class, KafkaPublisher::class)
class PermissionEo(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: String? = null,

    @Enumerated(EnumType.STRING)
    var category: PermissionCategory? = null,

    @Enumerated(EnumType.STRING)
    var type: PermissionType? = null,

    @Version
    var version: Long? = null
)
