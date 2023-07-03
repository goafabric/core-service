package org.goafabric.core.ui.adapter;

import org.goafabric.core.ui.SearchLogic;
import org.goafabric.core.ui.adapter.vo.AuditEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional
public class AuditTrailLogic implements SearchLogic<AuditEvent> {
    private final AuditTrailRepository auditTrailRepository;

    public AuditTrailLogic(AuditTrailRepository auditTrailRepository) {
        this.auditTrailRepository = auditTrailRepository;
    }

    @Override
    public List<AuditEvent> search(String search) {
        return auditTrailRepository.findByCreatedByStartsWithIgnoreCaseOrModifiedByStartsWithIgnoreCase(search, search);
    }
}
