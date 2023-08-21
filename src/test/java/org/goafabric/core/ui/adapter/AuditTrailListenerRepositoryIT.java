package org.goafabric.core.ui.adapter;

import org.goafabric.core.ui.extension.audittrail.AuditTrailRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class AuditTrailListenerRepositoryIT {
    @Autowired
    private AuditTrailRepository repository;
    
    @Test
    void findAll() {
        var audits = repository.findAll();
        assertThat(audits).isNotNull();

        assertThat(repository.findByCreatedByStartsWithIgnoreCaseOrModifiedByStartsWithIgnoreCase("import", "import")).isNotNull();
    }

}