package org.goafabric.core.process.steps;

import org.goafabric.core.process.BillingFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RulesValidator {
    private static final Logger log = LoggerFactory.getLogger(RulesValidator.class);

    public static void validate(BillingFile billingFile) {
        log.info("rule validating file with id: " + billingFile.id());
        if (billingFile.id().isEmpty()) {
            throw new IllegalStateException("Rule Validation failed");
        }
    }
}
