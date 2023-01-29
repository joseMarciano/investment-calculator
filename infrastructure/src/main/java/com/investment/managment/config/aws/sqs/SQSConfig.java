package com.investment.managment.config.aws.sqs;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class SQSConfig {
    private final String region;

    public SQSConfig(final @Value("${aws.region}") String region) {
        this.region = region;
    }

    @Bean
    @Primary
    public AmazonSQSAsync amazonSQSAsync(final AWSCredentialsProvider credentialsProvider) {
        return AmazonSQSAsyncClientBuilder.standard()
                .withCredentials(credentialsProvider)
                .withRegion(this.region)
                .build();
    }

    @Bean
    @Primary
    public QueueMessagingTemplate queueMessagingTemplate(final AmazonSQSAsync amazonSQSAsync) {
        return new QueueMessagingTemplate(amazonSQSAsync);
    }
}
