package com.example.project1.service;

import org.springframework.stereotype.Service;

@Service
public class TelegramService {
    private final TelegramHandler telegramHandler;

    public TelegramService(TelegramHandler telegramHandler) {
        this.telegramHandler = telegramHandler;
    }

    public boolean checkTelegram(String code) {
        return code.equals(telegramHandler.getCode());
    }
}
