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
    private String id;

    private String name;

    @ManyToMany//(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "role_permission",
            joinColumns = { @JoinColumn(name = "role_id") },
            inverseJoinColumns = { @JoinColumn(name = "permission_id") }
    )
    private List<PermissionEo> permissions;

    @Version //optimistic locking
    private Long version;

    private RoleEo() {}

    public RoleEo(String id, String name, List<PermissionEo> permissions, Long version) {
        this.id = id;
        this.name = name;
        this.permissions = permissions;
        this.version = version;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<PermissionEo> getPermissions() {
        return permissions;
    }

    public Long getVersion() {
        return version;
    }
}
