package exe201.studymatebackend.dto.response.quiz;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GenerateQuizResponse {
    private Integer quizSetID;
    private String quizSetName;
    private String createdBy;
    private LocalDateTime createdAt;
    private LocalDateTime expiredAt;
    private Integer duration;
    private String topic;
    private Integer numberOfQuestions;

    private List<QuestionResponse> questions;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class QuestionResponse {
        private String question;
        private List<String> options;
        private String answer;
    }
}
