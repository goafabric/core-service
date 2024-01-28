package org.goafabric.core.medicalrecords.logic.elastic;

import org.springframework.boot.actuate.autoconfigure.data.elasticsearch.ElasticsearchReactiveHealthContributorAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.elasticsearch.ElasticsearchRestHealthContributorAutoConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("jpa")
@Configuration
@EnableAutoConfiguration(exclude = {org.springframework.boot.autoconfigure.elasticsearch.ElasticsearchClientAutoConfiguration.class, org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchDataAutoConfiguration.class, org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchRepositoriesAutoConfiguration.class, ElasticsearchRestHealthContributorAutoConfiguration.class, ElasticsearchReactiveHealthContributorAutoConfiguration.class})
public class ElasticSearchConfiguration {
}
