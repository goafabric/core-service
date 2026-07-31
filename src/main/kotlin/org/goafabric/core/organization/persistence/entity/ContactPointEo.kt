package org.goafabric.core.organization.persistence.entity

import jakarta.persistence.*
import org.goafabric.core.persistence.extensions.AuditTrailListener
import org.goafabric.core.persistence.extensions.KafkaPublisher

@Entity
@Table(name = "contact_point")
@EntityListeners(AuditTrailListener::class, KafkaPublisher::class)
class ContactPointEo(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: String? = null,

    var use: String? = null,
    var system: String? = null,

    @Column(name = "c_value")
    var value: String? = null
)
