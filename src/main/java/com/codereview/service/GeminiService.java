package com.codereview.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
Return ONLY a raw JSON object (no markdown, no backticks, no explanation):

{
  "summary": "...",
  "bugs": ["...", "..."],
  "security": ["...", "..."],
  "performance": ["...", "..."],
  "improvements": ["...", "..."],
  "rating": 7
}

Analyze this %s code:

%s
""".formatted(language, code);

        String escapedPrompt = prompt
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "");

        String requestBody = """
                {
                  "contents": [{
                    "parts": [{
                      "text": "%s"
                    }]
                  }],
                  "generationConfig": {
                    "temperature": 0.3,
                    "maxOutputTokens": 1024
                  }
                }
                """.formatted(escapedPrompt);

        int maxRetries = 3;

        for (int attempt = 1; attempt <= maxRetries; attempt++) {
            try {
                String rawResponse = webClient.post()
                        .uri(apiUrl + "?key=" + apiKey)
                        .header("Content-Type", "application/json")
                        .bodyValue(requestBody)
                        .retrieve()
                        .bodyToMono(String.class)
                        .block();

                return extractText(rawResponse);

            } catch (WebClientResponseException e) {
                int status = e.getStatusCode().value();
                if (status == 429 && attempt < maxRetries) {
                    try { Thread.sleep(5000L * attempt); } catch (InterruptedException ie) { Thread.currentThread().interrupt(); }
                    continue;
                }
                String msg = status == 429 ? "Rate limit hit, please wait and retry." : status == 403 ? "API key is invalid or Gemini API is not enabled for this project." : "HTTP " + status + " error.";
                return errorJson("API error " + status + ": " + msg);

            } catch (Exception e) {
                if (attempt < maxRetries) {
                    try { Thread.sleep(3000L); } catch (InterruptedException ie) { Thread.currentThread().interrupt(); }
                    continue;
                }
                return errorJson("Request failed: " + e.getMessage());
            }
        }

        return errorJson("All retries exhausted. Please wait and try again.");
    }

    /**
     * Gemini returns: {"candidates":[{"content":{"parts":[{"text":"..."}]}}]}
     * We extract the value of the first "text" key using regex — no external libraries needed.
     */
    private String extractText(String raw) {
        if (raw == null) return errorJson("Empty response from API");

        // Match "text": "...content..." — handles escaped quotes inside
        Pattern p = Pattern.compile("\"text\"\\s*:\\s*\"((?:[^\"\\\\]|\\\\.)*)\"");
        Matcher m = p.matcher(raw);
        if (m.find()) {
            // Unescape the JSON string value
            return m.group(1)
                    .replace("\\n", "\n")
                    .replace("\\\"", "\"")
                    .replace("\\\\", "\\");
        }

        // Fallback: return raw if we can't parse
        return raw;
    }

    private String errorJson(String message) {
        return "{\"summary\":\"" + message.replace("\"", "'") + "\",\"bugs\":[],\"security\":[],\"performance\":[],\"improvements\":[],\"rating\":0}";
    }
}