package org.goafabric.core.organization.persistence.entity

import jakarta.persistence.*
import org.goafabric.core.persistence.extensions.AuditTrailListener
import org.goafabric.core.persistence.extensions.KafkaPublisher
import org.hibernate.annotations.TenantId
import java.time.LocalDateTime

@Entity
@Table(name = "locks")
@EntityListeners(AuditTrailListener::class, KafkaPublisher::class)
class LockEo(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: String? = null,

    @TenantId
    var organizationId: String? = null,

    var lockKey: String? = null,
    var lockTime: LocalDateTime? = null,
    var userName: String? = null
)
