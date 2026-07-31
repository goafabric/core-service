package org.goafabric.core.organization.persistence.entity

import jakarta.persistence.*
import org.goafabric.core.persistence.extensions.AuditTrailListener
import org.goafabric.core.persistence.extensions.KafkaPublisher
import java.time.LocalDate

@Entity
@Table(name = "practitioner")
@EntityListeners(AuditTrailListener::class, KafkaPublisher::class)
class PractitionerEo(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: String? = null,

    var givenName: String? = null,
    var familyName: String? = null,
    var gender: String? = null,
    var birthDate: LocalDate? = null,
    var lanr: String? = null,

    @OneToMany(cascade = [CascadeType.ALL])
    @JoinColumn(name = "practitioner_id")
    var address: MutableList<AddressEo> = mutableListOf(),

    @OneToMany(cascade = [CascadeType.ALL])
    @JoinColumn(name = "practitioner_id")
    var contactPoint: MutableList<ContactPointEo> = mutableListOf(),

    @Version
    var version: Long? = null
)
