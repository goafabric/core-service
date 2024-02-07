package org.goafabric.core.process.steps;

import org.goafabric.core.process.BillingFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LegalCheck {
    private static final Logger log = LoggerFactory.getLogger(LegalCheck.class);

    public static void check(BillingFile billingFile) {
        log.info("validating file with id: " + billingFile.id());
        if (!billingFile.id().equals("42")) {
            throw new IllegalStateException("Validation failed");
        }
    }

}
