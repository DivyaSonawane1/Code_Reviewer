package com.codereview.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class GeminiService {

    private final WebClient webClient;

    @Value("${gemini.api.key}")
    private String apiKey;

    @Value("${gemini.api.url}")
    private String apiUrl;

    public GeminiService(WebClient webClient) {
        this.webClient = webClient;
    }

    public String reviewCode(String code, String language) {

        String prompt = """
Return response in STRICT JSON format:

{
  "summary": "...",
  "bugs": ["...", "..."],
  "security": ["...", "..."],
  "performance": ["...", "..."],
  "improvements": ["...", "..."],
  "rating": 0
}

Analyze this %s code:

%s
""".formatted(language, code);

        String requestBody = """
                {
                  "contents": [{
                    "parts": [{
                      "text": "%s"
                    }]
                  }]
                }
                """.formatted(prompt.replace("\"", "\\\""));
                try{
        return webClient.post()
                .uri(apiUrl + "?key=" + apiKey)
                .header("Content-Type", "application/json")
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .block();
                }catch(Exception e){
                    return "⚠️ API limit reached. Please wait a minute and try again.";
                }
    }
}