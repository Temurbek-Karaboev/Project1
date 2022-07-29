package com.example.project1.service;

import com.example.project1.dto.AuthDTO;
import com.example.project1.model.Person;
import com.example.project1.repo.PersonRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import java.io.IOException;
import java.util.Map;
@Service
public class PersonService {
    private final PersonRepository personRepository;
    private final TelegramService telegramService;
    private final PasswordEncoder passwordEncoder;
    public PersonService(PersonRepository personRepository, TelegramService telegramService, PasswordEncoder passwordEncoder)  {
        this.personRepository = personRepository;
        this.telegramService = telegramService;
        this.passwordEncoder = passwordEncoder;
    }
    public  Mono<Map<String, String>> findByUsername(AuthDTO dto) {
        return personRepository.findByName(dto.getName()).flatMap(person -> {
            if (passwordEncoder.matches(dto.getPassword(), person.getPassword())){
                try {
                    telegramService.sendMessageToBot(person.getName());
                } catch (IOException | InterruptedException e) {
                    return Mono.error(new Exception(e));
                }
                return Mono.just(Map.of("Message", "Please sign in with Telegram ! \n http://localhost:8080/auth/telegram-check/message/"));
            }
            return Mono.just(Map.of("Error",  " Bad credentials " ));
        });
    }
    public Mono<Boolean> registerUser(Person person){
        return personRepository.existsByName(person.getName()).flatMap(data-> {
            if(Boolean.FALSE.equals(data)){
                person.setPassword(passwordEncoder.encode(person.getPassword()));
                person.setRole("ROLE_USER");
                return personRepository.save(person).flatMap(unused-> Mono.just(Boolean.TRUE));
            }
            else{
                return Mono.just(Boolean.FALSE);
            }

    });
    }
}

