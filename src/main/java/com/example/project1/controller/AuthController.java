package com.example.project1.controller;

import com.example.project1.dto.AuthDTO;
import com.example.project1.dto.TelegramDTO;
import com.example.project1.model.Person;
import com.example.project1.service.PersonService;
import com.example.project1.service.TelegramService;
import com.example.project1.util.JWTUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final PersonService personService;
    ObjectMapper objectMapper = new ObjectMapper();
    private final TelegramService telegramService;
    private final JWTUtil jwtUtil;

    public AuthController(PersonService personService, TelegramService telegramService, JWTUtil jwtUtil) {
        this.personService = personService;
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

    @PostMapping("/telegram-check/message/")
    public Mono<Map<String, String>> TelegramCheck(@RequestBody TelegramDTO telegramDTO) {
        return telegramService.checkTelegram(telegramDTO).flatMap(data -> {
            if (data) {
                String token = jwtUtil.generateToken(telegramDTO.getName());
                return Mono.just(Map.of("jwt-token", token));
            }
            return Mono.just(Map.of("Error", "ERROR CODE OR NAME"));
        });
    }

    @PostMapping("/login")
    public Mono<Map<String, String>> login(@RequestBody AuthDTO authDTO) {
        return personService.findByUsername(authDTO);
    }
}
