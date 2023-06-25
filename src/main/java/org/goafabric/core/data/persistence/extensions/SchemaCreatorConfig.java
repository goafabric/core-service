package org.goafabric.core.data.persistence.extensions;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.Map;

@Configuration
public class SchemaCreatorConfig {
    /** Flyway configuration to create database schemas **/

    @Bean
    public FlywayMigrationStrategy flywayMigrationStrategy() {
        return flyway -> {};
    }

    @Bean
    public Runnable schemaCreator(Flyway flyway,
                                  @Value("${multi-tenancy.migration.enabled}") Boolean enabled,
                                  @Value("${multi-tenancy.tenants}") String tenants,
                                  @Value("${multi-tenancy.schema-prefix:_}") String schemaPrefix) {
        return () -> {
            if (enabled) {
                Arrays.asList(tenants.split(",")).forEach(schema -> {
                            Flyway.configure()
                                    .configuration(flyway.getConfiguration())
                                    .schemas(schemaPrefix + schema)
                                    .defaultSchema(schemaPrefix + schema)
                                    .placeholders(Map.of("tenantId", schema))
                                    .load()
                                    .migrate();
                        }
                );
            }
        };
    }
}
