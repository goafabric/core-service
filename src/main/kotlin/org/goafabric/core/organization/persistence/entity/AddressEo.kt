package org.goafabric.core.organization.persistence.entity

import jakarta.persistence.*
import org.goafabric.core.persistence.extensions.AuditTrailListener
import org.goafabric.core.persistence.extensions.KafkaPublisher

@Entity
@Table(name = "address")
@EntityListeners(AuditTrailListener::class, KafkaPublisher::class)
class AddressEo(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: String? = null,

    var use: String? = null,
    var street: String? = null,
    var city: String? = null,
    var postalCode: String? = null,
    var state: String? = null,
    var country: String? = null,

    @Version
    var version: Long? = null
)
