package com.example.project1.service.mq.publisher;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class SendToRabbit {
    private final static String HOST_NAME = "localhost";
    private final static String QUEUE_NAME = "demo-lab-queue";
    public Mono<Boolean> senderMQ(String sender) throws Exception{
        final ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(HOST_NAME);
        try (final Connection connection = connectionFactory.newConnection();
             final Channel channel = connection.createChannel()) {
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            channel.basicPublish("", QUEUE_NAME, null, sender.getBytes());
        }
        catch (Exception e) {
            return Mono.just(false);
        }
        return Mono.just(true);
    }
}
