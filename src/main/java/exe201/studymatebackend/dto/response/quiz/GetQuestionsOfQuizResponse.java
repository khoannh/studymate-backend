package exe201.studymatebackend.dto.response.quiz;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetQuestionsOfQuizResponse {
    private Integer questionID;
    private String question;
    private String options;
    private String answer;
}
