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
    public String id;

    public String patientId;

    public String name;

    @OneToMany
    @JoinColumn(name = "user_id")
    public List<RoleEo> roles;

    @Version //optimistic locking
    public Long version;

}
