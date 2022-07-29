package com.example.project1.service;

import com.example.project1.dto.TelegramDTO;
import com.example.project1.repo.PersonRepository;
import com.example.project1.util.GenerateTelegramCode;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Service
public class TelegramService {
    public final static Map<String, String> map = new HashMap<String, String>();
    private static final String CHAT_ID = "1047067789";
    private static final String TOKEN = "5554490736:AAEIf3SCkORKKYg9wnAaVypWHVLj2Ttd_vQ";
    private final PersonRepository personRepository;
    public TelegramService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }
    public Mono<Boolean> checkTelegram(TelegramDTO telegramDTO) {
        return personRepository.findByName(telegramDTO.getName())
                .flatMap(data->{
                    String tgCode = map.get(data.getName());
                    return Mono.just(telegramDTO.getCode().equals(tgCode));
                });
    }
    public void sendMessageToBot(String name ) throws IOException, InterruptedException {
        String code = new GenerateTelegramCode().generateCode();
        map.put(name, code);
        HttpClient client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(5))
                .version(HttpClient.Version.HTTP_2)
                .build();
        UriBuilder builder = UriBuilder
                .fromUri("https://api.telegram.org")
                .path("/{token}/sendMessage")
                .queryParam("chat_id", CHAT_ID)
                .queryParam("text", code);
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(builder.build("bot" + TOKEN))
                .timeout(Duration.ofSeconds(5))
                .build();
        client
                .send(request, HttpResponse.BodyHandlers.ofString());
    }
}
