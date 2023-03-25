package com.investment.managment.api.user.websocket;

import com.investment.managment.user.models.UserOnlineRequest;
import com.investment.managment.user.online.UserOnlineCommandInput;
import com.investment.managment.user.online.UserOnlineUseCase;
import com.investment.managment.utils.Json;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

@Controller
@MessageMapping("user")
public class UserSocketRequest {

    private final UserOnlineUseCase userOnlineUseCase;

    public UserSocketRequest(final UserOnlineUseCase userOnlineUseCase) {
        this.userOnlineUseCase = userOnlineUseCase;
    }

    @MessageMapping("im-online")
    public void request(@Payload String payload) {
        final var request = Json.convertToObj(payload, UserOnlineRequest.class);
        this.userOnlineUseCase.execute(UserOnlineCommandInput.with(request.id(), request.online()));
        System.out.println(Thread.currentThread().getName());
    }

}
