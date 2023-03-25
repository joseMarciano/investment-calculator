package com.investment.managment.user.online;

import com.investment.managment.UnitUseCase;
import com.investment.managment.user.User;
import com.investment.managment.user.UserGateway;
import com.investment.managment.user.UserID;

import java.time.LocalDateTime;

public class UserOnlineUseCase extends UnitUseCase<UserOnlineCommandInput> {

    private final UserGateway userGateway;

    public UserOnlineUseCase(final UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    @Override
    public void execute(final UserOnlineCommandInput input) {
        userGateway.update(User
                .builder()
                .id(UserID.from(input.id()))
                .online(input.isOnline())
                .updatedAt(LocalDateTime.now())
                .build()
        );
    }
}
