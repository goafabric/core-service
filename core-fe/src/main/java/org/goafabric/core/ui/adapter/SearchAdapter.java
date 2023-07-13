package org.goafabric.core.ui.adapter;

import org.goafabric.core.ui.SearchLogic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter<T> implements SearchLogic<T> {
    @Autowired
    private RestTemplate restTemplate;

    private final String uri;

    public SearchAdapter(String uri) {
        this.uri = uri;
    }

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public List<T> search(String search) {
        try {
            return restTemplate.exchange(uri, HttpMethod.GET, null,
                    new ParameterizedTypeReference<List<T>>() {
                    }, search).getBody();
        } catch (Exception e) {
            log.warn("exception during rest: " + e.getMessage());
            return new ArrayList<>();
        }
    }


}
