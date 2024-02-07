package org.goafabric.core.process;

import org.goafabric.core.process.steps.LegalCheck;
import org.goafabric.core.process.steps.LegalEncrypter;
import org.goafabric.core.process.steps.RulesValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class BillingProcess {


    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private BillingFile billingFile;

    public static BillingProcess getInstance() {
        return new BillingProcess();
    }

    public BillingProcess create(String id) {
        this.billingFile = new BillingFile(id);
        return this;
    }

    public BillingProcess retrieveRecords() {
        RulesValidator.validate(billingFile);
        return this;
    }

    public BillingProcess validateRules() {
        RulesValidator.validate(billingFile);
        return this;
    }


    public BillingProcess check() {
        LegalCheck.check(billingFile);
        return this;
    }

    public BillingProcess encrypt() {
        billingFile = LegalEncrypter.encrypt(billingFile);
        return this;
    }

    public BillingProcess send() {
        log.info("sending file with id: " + billingFile.id());
        return this;
    }

    public BillingProcess createPDF() {
        log.info("creating pdf with id: " + billingFile.id());
        return this;
    }

    public BillingProcess storePDF() {
        log.info("creating pdf with id: " + billingFile.id());
        return this;
    }

}
