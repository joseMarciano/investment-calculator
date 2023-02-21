package com.investment.managment.api.websocket;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

@Controller
@MessageMapping("request")
public class WebSocketRequest {

    @MessageMapping("{requestType}")
    public void request(@DestinationVariable WebSocketRequesType requestType, @Payload String payload) {
        System.out.println(Thread.currentThread().getName());
        requestType.getUseCase().execute(payload);
    }

}
