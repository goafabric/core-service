package org.goafabric.core.medicalrecords.persistence.jpa.entity

import jakarta.persistence.*
import org.goafabric.core.persistence.extensions.AuditTrailListener
import org.goafabric.core.persistence.extensions.KafkaPublisher
import org.hibernate.annotations.TenantId
import java.time.LocalDate

@Entity
@Table(name = "encounter")
@EntityListeners(AuditTrailListener::class, KafkaPublisher::class)
class EncounterEo(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: String? = null,

    @TenantId
    var organizationId: String? = null,

    var patientId: String? = null,
    var practitionerId: String? = null,
    var encounterDate: LocalDate? = null,
    var encounterName: String? = null,

    @OneToMany(cascade = [CascadeType.ALL])
    @JoinColumn(name = "encounter_id")
    var medicalRecords: MutableList<MedicalRecordEo> = mutableListOf(),

    @Version
    var version: Long? = null
)
