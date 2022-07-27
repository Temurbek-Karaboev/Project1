package com.example.project1.controller;

import com.example.project1.dto.AuthDTO;
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
    public Mono<Map<String, String>> createPerson(@RequestBody Person person) {
       return personService.registerUser(person).flatMap(unused -> {
           String token = jwtUtil.generateToken(person.getName());
           return Mono.just(Map.of("jwt-token", token));
       });
    }

    @PostMapping("/telegram-check/{code}")
    public Mono<String > TelegramCheck(@PathVariable("code") String code){
return telegramService.checkTelegram(code).flatMap(data->{
    if(data){
        return Mono.just("You are successfully signed in") ;
    }
        return  Mono.just("ERROR CODE");
});
    }

    @PostMapping("/login")
    public  Mono<Map<String, String>> login(@RequestBody AuthDTO authDTO){
        return personService.findByUsername(authDTO);
    }




}
