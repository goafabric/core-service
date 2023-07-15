package org.goafabric.core.ui.audittrail;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AuditTrailRepository extends CrudRepository<AuditEvent, String> {
    List<AuditEvent> findByCreatedByStartsWithIgnoreCaseOrModifiedByStartsWithIgnoreCase(String createdBy, String modifiedBy);
}
