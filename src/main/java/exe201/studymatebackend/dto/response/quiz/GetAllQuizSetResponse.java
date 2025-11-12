package exe201.studymatebackend.dto.response.quiz;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetAllQuizSetResponse {

    private Integer quizSetID;
    private String quizSetName;
    private String createdBy;
    private LocalDateTime createdAt;
    private LocalDateTime expiredAt;
    private Integer duration;
    private String topic;
    private Integer numberOfQuestions;
}
