package org.goafabric.core.medicalrecords.persistence.jpa.entity

import jakarta.persistence.*
import org.goafabric.core.persistence.extensions.AuditTrailListener
import org.goafabric.core.persistence.extensions.KafkaPublisher

@Entity
@Table(name = "body_metrics")
@EntityListeners(AuditTrailListener::class, KafkaPublisher::class)
class BodyMetricsEo(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: String? = null,

    var bodyHeight: String? = null,
    var bellyCircumference: String? = null,
    var headCircumference: String? = null,
    var bodyFat: String? = null,

    @Version
    var version: Long? = null
)
