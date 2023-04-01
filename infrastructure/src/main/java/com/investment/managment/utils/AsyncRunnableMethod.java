package com.investment.managment.utils;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class AsyncRunnableMethod {


    @Async
    public void runAsync(Runnable... runnables) {
        Arrays.stream(runnables).forEach(Runnable::run);
    }
}
