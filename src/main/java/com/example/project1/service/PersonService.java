package com.example.project1.service;

import com.example.project1.dto.AuthDTO;
import com.example.project1.model.Person;
import com.example.project1.repo.PersonRepository;
import com.example.project1.util.JWTUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import javax.security.auth.login.CredentialException;
import java.util.Map;
@Service
public class PersonService {
    private final static String HOST_NAME = "localhost";
    private final static String QUEUE_NAME = "demo-lab-queue";
    private final PersonRepository personRepository;
    private final PasswordEncoder passwordEncoder;
    ObjectMapper objectMapper = new ObjectMapper();
    public PersonService(PersonRepository personRepository, PasswordEncoder passwordEncoder)  {
        this.personRepository = personRepository;
        this.passwordEncoder = passwordEncoder;
    }
    public  Mono<Map<String, String>> findByUsername(AuthDTO dto) {
        return personRepository.findByName(dto.getName()).flatMap(person -> {
            if (passwordEncoder.matches(dto.getPassword(), person.getPassword())){
                return Mono.just(Map.of("Message", "Please sign in with Telegram ! \n http://localhost:8080/auth/telegram-check/message/{code}"));
            }
            return Mono.just(Map.of("Error",  " Bad credentials " ));
        });
    }
    public Mono<Boolean> registerUser(Person person){
        return personRepository.existsByName(person.getName()).flatMap(data-> {
            if(Boolean.FALSE.equals(data)){
                person.setPassword(passwordEncoder.encode(person.getPassword()));
                person.setRole("ROLE_USER");
                return personRepository.save(person).flatMap(unused-> {
                    return Mono.just(Boolean.TRUE);
                });
            }
            else{
                return Mono.just(Boolean.FALSE);
            }

    });
    };

}

