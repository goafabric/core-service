package org.goafabric.core.organization.persistence.entity

import jakarta.persistence.*
import org.goafabric.core.persistence.extensions.AuditTrailListener
import org.goafabric.core.persistence.extensions.KafkaPublisher

@Entity
@Table(name = "organization")
@EntityListeners(AuditTrailListener::class, KafkaPublisher::class)
class OrganizationEo(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: String? = null,

    var name: String? = null,
    var bsnr: String? = null,

    @OneToMany(cascade = [CascadeType.ALL])
    @JoinColumn(name = "organization_id")
    var address: MutableList<AddressEo> = mutableListOf(),

    @OneToMany(cascade = [CascadeType.ALL])
    @JoinColumn(name = "organization_id")
    var contactPoint: MutableList<ContactPointEo> = mutableListOf(),

    @Version
    var version: Long? = null
)
