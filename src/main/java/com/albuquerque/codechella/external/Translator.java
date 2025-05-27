package com.albuquerque.codechella.external;

import com.albuquerque.codechella.dto.external.Translation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class Translator {

    @Value("${translator.api.url}")
    private static String BASE_URL;

    @Value("${translator.api.key}")
    private static String API_KEY;


    public static Mono<String> get(String text, String language) {
        WebClient webClient = WebClient.builder()
                .baseUrl(BASE_URL)
                .build();

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("text", text);
        params.add("target_lang", language);

        return webClient.post()
                .header("Authorization", "Tranlate API Key " + API_KEY)
                .body(BodyInserters.fromValue(params))
                .retrieve()
                .bodyToMono(Translation.class)
                .map(Translation::getText);
    }
}