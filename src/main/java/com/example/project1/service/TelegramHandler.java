package com.example.project1.service;

import com.example.project1.repo.PersonRepository;
import com.example.project1.util.GenerateTelegramCode;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Configuration
@PropertySource("classpath:application.properties")
@Data
public class TelegramHandler extends TelegramLongPollingBot {
    private final PersonService personService;
    private final PersonRepository personRepository;
    @Value("${telegrambot.name}")
    private String USERNAME;
    @Value("${telegrambot.token}")
    private String TOKEN;
    private String code;

    @Override
    public String getBotUsername() {
        return USERNAME;
    }

    @Override
    public String getBotToken() {
        return TOKEN;
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
         sender(message, "Send number like +998901234567");
        Optional<String> text = Optional.ofNullable(message.getText());
        if(text.isPresent()){
            personRepository.existsByPhoneNumber(text)
                    .flatMap(exist->{
                        if(Boolean.TRUE.equals(exist)){
                            code=new GenerateTelegramCode().generateCode();
                            sender(message,code);
                            return Mono.empty();
                        }
                        sender(message,"Error");
                        return Mono.empty();
                    }).subscribe();
        }
//        if(personRepository.findByPhoneNumber(text.isPresent())){
//            code =new GenerateTelegramCode().generateCode();
//            sender(message, code );
//        }
        else{
            sender(message, "Number not found !");
        }
    }

    @Bean
    public void runBot() {
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            TelegramHandler telegramBotHandler = new TelegramHandler(personService, personRepository);
            telegramBotHandler.setTOKEN(TOKEN);
            telegramBotHandler.setUSERNAME(USERNAME);
            telegramBotsApi.registerBot(telegramBotHandler);
        } catch (TelegramApiException e) {
            System.out.println(e.getMessage());
        }
    }

    public void sender(Message message, String text) {

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(message.getChatId()));
        sendMessage.setText(text);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

}
