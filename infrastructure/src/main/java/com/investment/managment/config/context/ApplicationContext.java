package com.investment.managment.config.context;

import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class ApplicationContext {

    private static org.springframework.context.ApplicationContext context;

    private final org.springframework.context.ApplicationContext applicationContext;

    public ApplicationContext(final org.springframework.context.ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @PostConstruct
    protected void loadApplicationContext(){
        context = applicationContext;
    }

    public static <T> T getBean(Class<T> clazz){
        return context.getBean(clazz);
    }
}
