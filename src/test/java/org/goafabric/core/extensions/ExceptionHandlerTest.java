package org.goafabric.core.extensions;

import org.goafabric.core.data.extensions.ExceptionHandler;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ExceptionHandlerTest {

    @Test
    void handleIllegalArgumentException() {
        assertThat(new ExceptionHandler()
                .handleIllegalArgumentException(new IllegalArgumentException()))
                .isNotNull();
    }

    @Test
    void testHandleIllegalStateException() {
        assertThat(new ExceptionHandler()
                .handleIllegalStateException(new IllegalStateException()))
                .isNotNull();
    }

    @Test
    void handleGeneralException() {
        assertThat(new ExceptionHandler()
                .handleGeneralException(new Exception()))
                .isNotNull();
    }
}