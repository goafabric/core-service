package org.goafabric.core.process;

import org.junit.jupiter.api.Test;

public class ProcessTest {

    @Test
    public void testFluentAPI() {
        BillingProcess
                .getInstance()
                .create("42")
                .retrieveRecords()
                .validateRules()
                .check()
                .encrypt()
                .send()
                .createPDF()
                .storePDF();
    }
    
}
