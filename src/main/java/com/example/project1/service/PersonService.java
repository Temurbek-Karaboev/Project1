package com.example.project1.service;

import com.example.project1.dto.AuthDTO;
import com.example.project1.model.Person;
import com.example.project1.repo.PersonRepository;
import com.example.project1.util.JWTUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
public class PersonService {
    private final PersonRepository personRepository;
    private final JWTUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    ObjectMapper objectMapper = new ObjectMapper();




    public PersonService(PersonRepository personRepository, JWTUtil jwtUtil, PasswordEncoder passwordEncoder) {
        this.personRepository = personRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    public  Mono<Map<String, String>> findByUsername(AuthDTO dto) {
        return personRepository.findByName(dto.getName()).flatMap(person -> {

            if (passwordEncoder.matches(dto.getPassword(), person.getPassword())){
                String token = jwtUtil.generateToken(person.getName());
                return Mono.just(Map.of("jwt-token", token));
            }
            return Mono.just(Map.of("Error",  " Bad credentials " ));
        });
    }

    public Mono<String> registerUser(Person person){
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        person.setRole("ROLE_USER");
        person.setStep("login-passed");
        return personRepository.save(person).flatMap(data -> {
            System.out.println(data);
            return Mono.just("test");
        });
    }




}
