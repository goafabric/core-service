package org.goafabric.core.ui.auto;

import org.springframework.boot.actuate.autoconfigure.data.mongo.MongoHealthContributorAutoConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class DBAutoConfig {

    @Configuration
    @Profile("jpa")
    @EnableAutoConfiguration(exclude = {org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration.class, org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration.class, org.springframework.boot.autoconfigure.data.mongo.MongoRepositoriesAutoConfiguration.class, MongoHealthContributorAutoConfiguration.class})
    class JpaConfiguration {
    }

    @Configuration
    @Profile("mongodb")
    @EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class, JpaRepositoriesAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
    class MongoConfiguration {
    }
}
