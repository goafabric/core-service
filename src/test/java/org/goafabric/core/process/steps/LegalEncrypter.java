package org.goafabric.core.process.steps;

import org.goafabric.core.process.BillingFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class LegalEncrypter {
    private static final Logger log = LoggerFactory.getLogger(LegalEncrypter.class);

    public static BillingFile encrypt(BillingFile billingFile) {
        log.info("encrypting file with id: " + billingFile.id());
        return new BillingFile(Base64.getEncoder().encodeToString(billingFile.id().getBytes(StandardCharsets.UTF_8)));
    }
}
