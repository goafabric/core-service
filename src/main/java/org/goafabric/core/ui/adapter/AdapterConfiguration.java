package org.goafabric.core.ui.adapter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.Collections;

@Configuration
public class AdapterConfiguration {

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder,
                                     @Value("${adapter.timeout:10000}") Integer timeout) {
        RestTemplate restTemplate = builder
                .setConnectTimeout(Duration.ofMillis(timeout))
                .setReadTimeout(Duration.ofMillis(timeout))
                .build();

        restTemplate.setMessageConverters(Collections.singletonList(new MappingJackson2HttpMessageConverter()));
        restTemplate.getInterceptors().add((request, body, execution) -> {
            request.getHeaders().set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
            //TenantContext.getAdapterHeaderMap().forEach((key, value) -> request.getHeaders().set(key, value));
            return execution.execute(request, body);
        });
        return restTemplate;
    }

}
