package com.investment.managment.execution;

import com.investment.managment.execution.gateway.ExecutionNotification;
import com.investment.managment.execution.models.pnl.PnlNotificationRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class ExecutionNotificationImpl implements ExecutionNotification {

    private final QueueMessagingTemplate queueMessagingTemplate;

    private final String pnlOpenQueue;

    public ExecutionNotificationImpl(final QueueMessagingTemplate queueMessagingTemplate,
                                     final @Value("${aws.sqs.execution-pnl-open-changed-queue}") String pnlOpenQueue) {
        this.queueMessagingTemplate = queueMessagingTemplate;
        this.pnlOpenQueue = pnlOpenQueue;
    }

    @Override
    public void notifyPnlOpen(final Execution execution) {
        this.queueMessagingTemplate.convertAndSend(this.pnlOpenQueue, PnlNotificationRequest.with(execution.getId().getValue(), execution.getPnlOpen(), execution.getPnlOpenPercentage()));
    }
}
