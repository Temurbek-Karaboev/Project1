package com.example.project1.service;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class TelegramService {
    private final TelegramHandler telegramHandler;

    public TelegramService(TelegramHandler telegramHandler) {
        this.telegramHandler = telegramHandler;
    }

//     public boolean checkTg(String code){
//         return code.equals(telegramHandler.getCode());
//     }

    public Mono<Boolean> checkTelegram(String code) {
        boolean result = code.equals(telegramHandler.getCode());
        return Mono.just(result);

        }
}
