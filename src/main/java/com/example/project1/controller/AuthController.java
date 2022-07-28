package com.example.project1.controller;

import com.example.project1.dto.AuthDTO;
import com.example.project1.model.Person;
import com.example.project1.service.PersonService;
import com.example.project1.service.TelegramService;
import com.example.project1.service.mq.publisher.SendToRabbit;
import com.example.project1.util.JWTUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import java.util.Map;
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final PersonService personService;
    private final SendToRabbit sendToRabbit;
    ObjectMapper objectMapper = new ObjectMapper();
    private final TelegramService telegramService;
    private final JWTUtil jwtUtil;
    public AuthController(PersonService personService, SendToRabbit sendToRabbit, TelegramService telegramService, JWTUtil jwtUtil) {
        this.personService = personService;
        this.sendToRabbit = sendToRabbit;
        this.telegramService = telegramService;
        this.jwtUtil = jwtUtil;
    }
    @PostMapping("/registration")
    public Mono<String> createPerson(@RequestBody Person person) {
        return personService.registerUser(person).flatMap(data -> {
            if (data) {
                return Mono.just("Please sign in! \n http://localhost:8080/auth/login");
            }
            return Mono.just("ERROR THIS NAME ALREADY TAKEN");
        });
    }
    @PostMapping("/telegram-check/message/{code}")
    public Mono<Map<String, String>> TelegramCheck(@PathVariable("code") String code, Authentication authentication) {
        return telegramService.checkTelegram(code, authentication).flatMap(unused -> {
            String token = jwtUtil.generateToken(authentication.getName());
            return Mono.just(Map.of("jwt-token", token));
        });
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
    @PostMapping("/login")
    public Mono<Map<String, String>> login(@RequestBody AuthDTO authDTO) {
        return personService.findByUsername(authDTO);
    }
}
