package org.goafabric.core.organization.persistence.entity

import jakarta.persistence.*
import org.goafabric.core.persistence.extensions.AuditTrailListener
import org.goafabric.core.persistence.extensions.KafkaPublisher
import org.hibernate.annotations.TenantId
import java.time.LocalDate

@Entity
@Table(name = "patient")
@EntityListeners(AuditTrailListener::class, KafkaPublisher::class)
class PatientEo(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: String? = null,

    @TenantId
    var organizationId: String? = null,

    var givenName: String? = null,
    var givenSoundex: String? = null,
    var familyName: String? = null,
    var familySoundex: String? = null,
    var gender: String? = null,
    var birthDate: LocalDate? = null,

    @OneToMany(cascade = [CascadeType.ALL])
    @JoinColumn(name = "patient_id")
    var address: MutableList<AddressEo> = mutableListOf(),

    @OneToMany(cascade = [CascadeType.ALL])
    @JoinColumn(name = "patient_id")
    var contactPoint: MutableList<ContactPointEo> = mutableListOf(),

    @Version
    var version: Long? = null
)
