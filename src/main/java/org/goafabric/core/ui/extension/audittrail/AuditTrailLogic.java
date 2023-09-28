package org.goafabric.core.ui.extension.audittrail;

import org.goafabric.core.ui.adapter.SearchAdapter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional
public class AuditTrailLogic implements SearchAdapter<AuditEvent> {
    private final AuditTrailRepository auditTrailRepository;

    public AuditTrailLogic(AuditTrailRepository auditTrailRepository) {
        this.auditTrailRepository = auditTrailRepository;
    }

    @Override
    public List<AuditEvent> search(String search) {
        return auditTrailRepository.findByCreatedByStartsWithIgnoreCaseOrModifiedByStartsWithIgnoreCase(search, search);
    }
}
