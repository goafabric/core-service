package org.goafabric.core.ui.extension;/*
package org.goafabric.core.ui.configuration;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.server.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CustomErrorHandler implements VaadinServiceInitListener {

    private static final Logger logger = LoggerFactory.getLogger(CustomErrorHandler.class);
    
    @Override
    public void serviceInit(ServiceInitEvent event) {
        event.getSource().addSessionInitListener( init -> {
            VaadinSession.getCurrent().setErrorHandler(new ErrorHandler() {
                @Override
                public void error(ErrorEvent errorEvent) {
                    logger.error("Something wrong happened", errorEvent.getThrowable());
                    if(UI.getCurrent() != null) {
                        UI.getCurrent().access(() -> {
                            Notification.show("An internal error has occurred. " + errorEvent.getThrowable().getMessage());
                        });
                    }

                }
            });
        });
    }
}

 */
