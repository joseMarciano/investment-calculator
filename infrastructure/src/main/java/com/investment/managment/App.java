package com.investment.managment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.aws.autoconfigure.context.ContextInstanceDataAutoConfiguration;
import org.springframework.cloud.aws.autoconfigure.context.ContextRegionProviderAutoConfiguration;
import org.springframework.cloud.aws.autoconfigure.context.ContextStackAutoConfiguration;

@SpringBootApplication
@EnableAutoConfiguration(
        exclude = {
                ContextInstanceDataAutoConfiguration.class,
                ContextStackAutoConfiguration.class,
                ContextRegionProviderAutoConfiguration.class
        }
)
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

}
