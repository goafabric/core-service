package org.goafabric.core.organization.repository.entity;

import jakarta.persistence.*;
import org.goafabric.core.organization.repository.extensions.AuditTrailListener;

import java.util.List;

@Entity
@Table(name = "roles")
@EntityListeners(AuditTrailListener.class)
public class RoleEo {
    @Id
    @org.springframework.data.annotation.Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public String id;

    public String name;

    @ManyToMany//(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "role_permission",
            joinColumns = { @JoinColumn(name = "role_id") },
            inverseJoinColumns = { @JoinColumn(name = "permission_id") }
    )
    public List<PermissionEo> permissions;

    @Version //optimistic locking
    public Long version;

}
