package org.goafabric.core;

import io.awspring.cloud.autoconfigure.core.AwsAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * Created by amautsch on 26.06.2015.
 */

@SpringBootApplication(exclude = AwsAutoConfiguration.class)
public class Application {

    public static void main(String[] args){
        SpringApplication.run(Application.class, args);
    }

}
