package com.example.project1.service;

import com.example.project1.repo.PersonRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class TelegramService {
    private final TelegramHandler telegramHandler;
    private final PersonRepository personRepository;

    public TelegramService(TelegramHandler telegramHandler, PersonRepository personRepository) {
        this.telegramHandler = telegramHandler;
        this.personRepository = personRepository;
    }

//     public boolean checkTg(String code){
//         return code.equals(telegramHandler.getCode());
//     }

    public Mono<Boolean> checkTelegram(String code, Authentication auth) {
        return personRepository.findByName(auth.getName())
                .flatMap(data->{
                    String tgCode = telegramHandler.map.get(data.getPhoneNumber());
                    return Mono.just(code.equals(tgCode));
                });


    }
}
