package com.investment.managment.utils;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class AsyncRunnableMethod {


    @Async
    public void runAsync(Runnable... runnables) {
        for (Runnable runnable : runnables) {
            System.out.println(Thread.currentThread().getName());
            runnable.run();
        }
    }
}
