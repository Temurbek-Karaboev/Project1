package com.example.project1.controller;

import com.example.project1.model.Person;
import com.example.project1.service.PersonService;
import com.example.project1.service.TelegramService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final PersonService personService;
    ObjectMapper objectMapper = new ObjectMapper();
    private final TelegramService telegramService;

    public AuthController(PersonService personService, TelegramService telegramService) {
        this.personService = personService;
        this.telegramService = telegramService;
    }

    @PostMapping("/registration")
    public String createPerson(@RequestBody Person person) {
        personService.registerUser(person);
        return "Send /start to Bot http://t.me/auth_project_robot ";
    }

    @GetMapping("/telegram-message/{phoneNumber}")
    public String TelegramAuth(@PathVariable String phoneNumber){
        Person person = objectMapper.convertValue(phoneNumber, Person.class);
        personService.registerUser(person);

        return "Code has been sent via Telegram";
    }

    @PostMapping("/telegram-check")
    public String TelegramCheck(@RequestParam String code){

        if(telegramService.checkTelegram(code)){
            return "You are successfully signed in";
        }
        else {
            return "ERROR";
        }
    }
}
