package com.example.project1.repo;

import com.example.project1.model.Person;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Repository
public interface PersonRepository extends R2dbcRepository<Person, Long> {
    Mono<Person> findByName(String name);
    Mono<Boolean> existsByPhoneNumber(Optional<String > phoneNumber);


}
