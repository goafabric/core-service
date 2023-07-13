package org.goafabric.core.ui.adapter;

import org.goafabric.core.ui.SearchLogic;
import org.goafabric.core.ui.adapter.vo.Practitioner;
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
@RegisterReflectionForBinding(Practitioner.class)
public class PractitionerAdapter implements SearchLogic<Practitioner> {
    @Autowired private RestTemplate restTemplate;

    @Value("${frontend.core-service.uri}") private String uri;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public List<Practitioner> search(String search) {
        try {
            return restTemplate.exchange(uri + "/practitioners/findByFamilyName?familyName={search}", HttpMethod.GET, null,
                    new ParameterizedTypeReference<List<Practitioner>>() {
                    }, search).getBody();
        } catch (Exception e) {
            log.warn("exception during rest: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}
