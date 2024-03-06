package org.goafabric.core.organization.persistence.entity;

import jakarta.persistence.*;
import org.goafabric.core.organization.persistence.extensions.AuditTrailListener;
import org.hibernate.annotations.TenantId;

import java.time.LocalDateTime;

@Entity
@Table(name = "locks")
@EntityListeners(AuditTrailListener.class)
public class LockEo {
    @Id
    @org.springframework.data.annotation.Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @TenantId
    private String organizationId;

    private String          lockKey;
    private LocalDateTime   lockTime;
    private String          userName;

    private LockEo() {}

    public LockEo(String id, String lockKey, LocalDateTime lockTime, String userName) {
        this.id = id;
        this.lockKey = lockKey;
        this.lockTime = lockTime;
        this.userName = userName;
    }

    public String getId() {
        return id;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public String getLockKey() {
        return lockKey;
    }

    public LocalDateTime getLockTime() {
        return lockTime;
    }

    public String getUserName() {
        return userName;
    }
}
