package com.example.project1.service.mq.publisher;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class SendToRabbit {
    @Value("${rabbit.host.name}")
    private String HOST_NAME ;
    @Value("${rabbit.host.queue.send}")
    private String QUEUE_NAME ;
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
