package org.goafabric.core;

import org.springframework.aot.hint.MemberCategory;
import org.springframework.aot.hint.annotation.RegisterReflection;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;


/**
 * Created by amautsch on 26.06.2015.
 */

@SpringBootApplication
@RegisterReflection(classes = {org.hibernate.boot.model.relational.ColumnOrderingStrategyStandard.class, org.hibernate.boot.models.annotations.internal.CacheAnnotation.class, org.hibernate.annotations.DialectOverride.class, org.hibernate.boot.models.DialectOverrideAnnotations.class,
        org.hibernate.validator.internal.util.logging.Log_$logger.class, org.hibernate.engine.internal.VersionLogger_$logger.class, org.hibernate.internal.SessionFactoryRegistryMessageLogger_$logger.class,
        org.hibernate.dialect.type.PostgreSQLInetJdbcType.class,  org.hibernate.dialect.type.PostgreSQLIntervalSecondJdbcType.class, org.hibernate.dialect.type.PostgreSQLStructPGObjectJdbcType.class, org.hibernate.dialect.type.PostgreSQLJsonPGObjectJsonbType.class, org.hibernate.dialect.type.PostgreSQLJsonArrayPGObjectJsonbJdbcTypeConstructor.class,
        org.hibernate.boot.archive.scan.internal.ScannerLogger_$logger.class, org.hibernate.resource.jdbc.internal.ResourceRegistryLogger_$logger.class,
        org.flywaydb.core.internal.configuration.extensions.PrepareScriptFilenameConfigurationExtension.class, tools.jackson.databind.jsontype.NamedType.class, org.hibernate.boot.models.annotations.internal.TenantIdAnnotation.class
}, memberCategories = {MemberCategory.INVOKE_DECLARED_CONSTRUCTORS, MemberCategory.INVOKE_DECLARED_METHODS, MemberCategory.ACCESS_DECLARED_FIELDS})
public class Application {

    public static void main(String[] args){
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner init(ApplicationContext context) {
        return args -> {
            if ((args.length > 0) && ("-check-integrity".equals(args[0]))) { SpringApplication.exit(context, () -> 0);}
        };
    }

}
