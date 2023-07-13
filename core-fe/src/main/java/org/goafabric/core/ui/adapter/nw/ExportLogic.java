package org.goafabric.core.ui.adapter.nw;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ExportLogic {
    @Autowired private RestTemplate restTemplate;

    @Value("${frontend.core-service.uri}") private String uri;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public void run(String path) {
        try {
            restTemplate.getForObject(uri + "/exports/run?path={path}", Object.class, path);
        } catch (Exception e) {
            log.warn("exception during rest: " + e.getMessage());
        }

    }
}
