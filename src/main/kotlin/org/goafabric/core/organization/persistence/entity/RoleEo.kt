package org.goafabric.core.organization.persistence.entity

import jakarta.persistence.*
import org.goafabric.core.persistence.extensions.AuditTrailListener
import org.goafabric.core.persistence.extensions.KafkaPublisher

@Entity
@Table(name = "roles")
@EntityListeners(AuditTrailListener::class, KafkaPublisher::class)
class RoleEo(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: String? = null,

    var name: String? = null,

    @ManyToMany
    @JoinTable(
        name = "role_permission",
        joinColumns = [JoinColumn(name = "role_id")],
        inverseJoinColumns = [JoinColumn(name = "permission_id")]
    )
    var permissions: MutableList<PermissionEo> = mutableListOf(),

    @Version
    var version: Long? = null
)
