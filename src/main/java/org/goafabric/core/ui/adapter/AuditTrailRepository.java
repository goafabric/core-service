package org.goafabric.core.ui.adapter;

import org.goafabric.core.ui.adapter.vo.AuditEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditTrailRepository extends JpaRepository<AuditEvent, String> {
}
