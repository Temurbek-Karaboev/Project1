package com.example.project1.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @PostMapping("/registration")
    public String createPerson(@RequestBody Person person) {
        personService.registerUser(person);
        return "redirect:/login";
    }
}
