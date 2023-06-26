package org.goafabric.core;

import io.awspring.cloud.autoconfigure.core.AwsAutoConfiguration;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportRuntimeHints;


/**
 * Created by amautsch on 26.06.2015.
 */

@SpringBootApplication(exclude = AwsAutoConfiguration.class)
@ImportRuntimeHints(Application.applicationRuntimeHints.class)
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

    @RegisterReflectionForBinding({org.hibernate.binder.internal.TenantIdBinder.class, org.hibernate.generator.internal.TenantIdGeneration.class})
    static class applicationRuntimeHints implements RuntimeHintsRegistrar {
        @Override
        public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
            hints.resources().registerPattern("en/*.yml"); //needed for stupid faker
        }
    }
}
