package org.goafabric.core.ui.adapter;

import org.goafabric.core.ui.adapter.vo.Condition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Component
@RegisterReflectionForBinding(Condition.class)
public class ConditionAdapter {
    @Autowired private RestTemplate restTemplate;

    @Value("${frontend.catalog-service.uri}") private String uri;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public List<Condition> findByDisplay(String search) {
        try {
            return restTemplate.exchange(uri + "/diagnosis/findByDisplay?display={display}", HttpMethod.GET, null,
                    new ParameterizedTypeReference<List<Condition>>() {}, search).getBody();
        } catch (Exception e) {
            log.warn("exception during rest: " + e.getMessage());
            return new ArrayList<>();
        }
    }

}
