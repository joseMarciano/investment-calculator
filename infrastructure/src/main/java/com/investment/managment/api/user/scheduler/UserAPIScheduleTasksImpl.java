package com.investment.managment.api.user.scheduler;

import com.investment.managment.user.UserGateway;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Component
public class UserAPIScheduleTasksImpl implements UserAPIScheduleTasks {

    private final UserGateway userGateway;

    private final SimpMessagingTemplate messagingTemplate;


    public UserAPIScheduleTasksImpl(final UserGateway userGateway,
                                    final SimpMessagingTemplate messagingTemplate) {
        this.userGateway = userGateway;
        this.messagingTemplate = messagingTemplate;
    }


    @Override
    @Scheduled(fixedDelay = 15000)
    public void checkIfUserStillOnline() {
        final var usersOnline = this.userGateway.findAll();

        usersOnline.forEach(it -> {
            final var now = LocalDateTime.now();
            final var updatedAt = it.getUpdatedAt();
            final long between = ChronoUnit.MINUTES.between(updatedAt, now);

            if (between < 15) return;
            messagingTemplate.convertAndSendToUser(it.getId().getValue(), "am-i-online", "");
            this.userGateway.removeById(it.getId());

        });

    }


}
