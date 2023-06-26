package org.goafabric.core.files.importexport.logic;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ExportLogicIT {
    @Autowired
    private ExportLogic exportLogic;

    @Autowired
    private ImportLogic importLogic;

    @Test
    void export() {
        var tempDir = System.getProperty("java.io.tmpdir");

        exportLogic.run(tempDir);
        importLogic.run(tempDir);
    }
}
