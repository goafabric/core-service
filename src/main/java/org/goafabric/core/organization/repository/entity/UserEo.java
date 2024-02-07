package org.goafabric.core.organization.repository.entity;

import jakarta.persistence.*;
import org.goafabric.core.organization.repository.extensions.AuditTrailListener;

import java.util.List;

@Entity
@Table(name = "users")
@EntityListeners(AuditTrailListener.class)
public class UserEo {
    @Id
    @org.springframework.data.annotation.Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String practitionerId;

    private String name;

    @ManyToMany//(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "user_role",
            joinColumns = { @JoinColumn(name = "user_id") },
            inverseJoinColumns = { @JoinColumn(name = "role_id") }
    )
    private List<RoleEo> roles;

    @Version //optimistic locking
    private Long version;

    private UserEo() {}

    public UserEo(String id, String practitionerId, String name, List<RoleEo> roles, Long version) {
        this.id = id;
        this.practitionerId = practitionerId;
        this.name = name;
        this.roles = roles;
        this.version = version;
    }

    public String getId() {
        return id;
    }

    public String getPractitionerId() {
        return practitionerId;
    }

    public String getName() {
        return name;
    }

    public List<RoleEo> getRoles() {
        return roles;
    }

    public Long getVersion() {
        return version;
    }
}
