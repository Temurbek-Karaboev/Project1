package com.example.project1.controller;

import com.example.project1.service.mq.publisher.SendToRabbit;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController()
@RequestMapping("/api/v1")
public class RabbitController {
    private final SendToRabbit sendToRabbit;
    public RabbitController(SendToRabbit sendToRabbit) {
        this.sendToRabbit = sendToRabbit;
    }
    @PostMapping("/rabbit-sender")
    public Mono<String> RabbitMQSender(@RequestBody String message) throws Exception {
        return sendToRabbit.senderMQ(message).flatMap(data -> {
            if (data) {
                return Mono.just("Your message was successfully sent");
            }
            return Mono.just("ERROR ERROR ERROR");
        });
    }
}
