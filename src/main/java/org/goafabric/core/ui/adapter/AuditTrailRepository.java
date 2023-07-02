package org.goafabric.core.ui.adapter;

import org.goafabric.core.ui.adapter.vo.AuditEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuditTrailRepository extends JpaRepository<AuditEvent, String> {
    List<AuditEvent> findByCreatedByStartsWithIgnoreCaseOrModifiedByStartsWithIgnoreCase(String createdBy, String modifiedBy);
}
