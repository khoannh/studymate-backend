package exe201.studymatebackend.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import exe201.studymatebackend.exception.AppException;
import exe201.studymatebackend.exception.ErrorCode;
import exe201.studymatebackend.service.GeminiService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.time.Duration;

@Service
public class GeminiServiceImpl implements GeminiService {

    private final String apiKey;
    private final WebClient webClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public GeminiServiceImpl(@Value("${gemini.api.key}") String apiKey) {
        this.apiKey = apiKey;
        this.webClient = WebClient.builder()
                .baseUrl("https://generativelanguage.googleapis.com")
                .build();
    }

    @Override
    public String generateQuizContent(String topic, Integer numberOfQuestions) {
        try {
            // 🧠 Tạo prompt
            String prompt = String.format("""
                    Hãy tạo %d câu hỏi trắc nghiệm về chủ đề "%s".
                    Trả về kết quả ở dạng JSON có cấu trúc như sau:
                    [
                      {
                        "question": "...",
                        "options": ["A", "B", "C", "D"],
                        "answer": "B"
                      }
                    ]
                    """, numberOfQuestions, topic);

            // 🧩 JSON request body
            String requestBody = String.format("""
                    {
                      "contents": [
                        {
                          "parts": [
                            { "text": %s }
                          ]
                        }
                      ]
                    }
                    """, objectMapper.writeValueAsString(prompt));

            // 🔥 Gọi API Gemini
            String response = webClient.post()
                    .uri("/v1beta/models/gemini-2.5-flash:generateContent",
                            uriBuilder -> uriBuilder.queryParam("key", apiKey).build())
                    .header("Content-Type", "application/json")
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(String.class)
                    .timeout(Duration.ofSeconds(60))
                    .block();

            // 🧾 Xử lý kết quả trả về
            JsonNode json = objectMapper.readTree(response);
            JsonNode candidates = json.path("candidates");

            if (candidates.isMissingNode() || candidates.isEmpty()) {
                throw new AppException(ErrorCode.GEMINI_CANNOT_FOUND_RESPONSE);
            }

            String text = candidates.get(0)
                    .path("content").path("parts").get(0)
                    .path("text").asText()
                    .trim();

            // 🧹 Loại bỏ markdown nếu có
            if (text.startsWith("```")) {
                text = text.replaceAll("```json|```", "").trim();
            }

            // ✅ Kiểm tra JSON hợp lệ
            objectMapper.readTree(text);

            return text;

        } catch (WebClientResponseException e) {
            throw new RuntimeException("Gemini API HTTP error: " + e.getRawStatusCode()
                    + " - " + e.getResponseBodyAsString(), e);
        } catch (Exception e) {
            throw new RuntimeException("Gemini API call failed: " + e.getMessage(), e);
        }
    }
}
