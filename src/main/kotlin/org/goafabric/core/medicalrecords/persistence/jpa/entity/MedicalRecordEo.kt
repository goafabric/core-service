package org.goafabric.core.medicalrecords.persistence.jpa.entity

import jakarta.persistence.*
import org.goafabric.core.medicalrecords.controller.dto.MedicalRecordType
import org.goafabric.core.persistence.extensions.AuditTrailListener
import org.goafabric.core.persistence.extensions.KafkaPublisher

@Entity
@Table(name = "medical_record")
@EntityListeners(AuditTrailListener::class, KafkaPublisher::class)
class MedicalRecordEo(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: String? = null,

    @Column(name = "encounter_id", insertable = false, updatable = false)
    var encounterId: String? = null,

    @Enumerated(EnumType.STRING)
    var type: MedicalRecordType? = null,

    var display: String? = null,
    var code: String? = null,
    var specialization: String? = null,

    @Version
    var version: Long? = null
)
