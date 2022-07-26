package com.example.project1.service;

import com.example.project1.model.Person;
import com.example.project1.repo.PersonRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


import java.util.List;

@Service
public class PersonService implements ReactiveUserDetailsService {
    private final PersonRepository personRepository;
    private final PasswordEncoder passwordEncoder;
    ObjectMapper objectMapper = new ObjectMapper();




    public PersonService(PersonRepository personRepository, PasswordEncoder passwordEncoder) {
        this.personRepository = personRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Mono<UserDetails> findByUsername(String name) {
        return personRepository.findByName(name)
                .map(person -> {
                            return new User(person.getName(), person.getPassword(), List.of(new SimpleGrantedAuthority(person.getRole())));
                        }
                );
    }

    public void registerUser(Person person) {
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        person.setRole("ROLE_TELEGRAM");
        personRepository.save(objectMapper.convertValue(person, Person.class)).subscribe();
    }


}
